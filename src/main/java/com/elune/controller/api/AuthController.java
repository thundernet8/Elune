/**
 * Elune - Lightweight Forum Powered by Razor.
 * Copyright (C) 2017, Touchumind<chinash2010@gmail.com>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.elune.controller.api;

import com.elune.configuration.AppConfiguration;
import com.elune.constants.Constant;
import com.elune.entity.UserEntity;
import com.elune.model.*;
import com.elune.service.*;
import com.elune.utils.StringUtil;
import com.elune.constants.CoinRewards;

import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.*;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.Session;

import java.util.HashMap;
import java.util.Map;

import static com.elune.constants.UserLogType.*;
import static com.elune.constants.BalanceLogType.*;

/**
 * @author Touchumind
 */
// TODO csrf header verify
@RoutePrefix("api/v1")
public class AuthController extends APIController{

    @FromService
    private UserService userService;

    @FromService
    private UserMetaService userMetaService;

    @FromService
    private BalanceMQService balanceMQService;

    @FromService
    private UserLogMQService userLogMQService;

    @FromService
    private AppConfiguration appConfiguration;

    @FromService BalanceLogService balanceLogService;

    @HttpPost
    @Route("user/me")
    public void checkMe() {

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
            if (uid < 1) {

                throw new HttpException("尚未登录", 200);
            }

            LoginUser user = userService.getLoginUser(uid);
            user.setFavoriteTopicIds(userMetaService.getFavoriteIds(uid));
            user.setFollowingTopicIds(userMetaService.getFollowingTopicIds(uid));
            user.setFollowingUserIds(userMetaService.getFollowingUids(uid));
            user.setBalance(balanceLogService.getBalance(uid));
            user.setDailySigned(userMetaService.hasSignedToday(uid));

            session.addAttribute("uid", user.getId());
            session.addAttribute("username", user.getUsername());
            session.addAttribute("email", user.getEmail());
            Map<String, Object> resp = new HashMap<>(2);
            resp.put("result", user);
            resp.put("msg", "获取用户信息成功");
            Succeed(resp);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("signin")
    public void login(@FromBody LoginModel loginModel) {

        try {

            LoginUser user = userService.signin(loginModel);
            user.setFavoriteTopicIds(userMetaService.getFavoriteIds(user.getId()));
            user.setFollowingTopicIds(userMetaService.getFollowingTopicIds(user.getId()));
            user.setFollowingUserIds(userMetaService.getFollowingUids(user.getId()));
            user.setBalance(balanceLogService.getBalance(user.getId()));
            user.setDailySigned(userMetaService.hasSignedToday(user.getId()));

            // log
            userLogMQService.createUserLog(user.getId(), user.getUsername(), L_LOGIN, "", "loggedIn", "", Request().getIp(), Request().getUa());

            Session session = Request().session();
            session.addAttribute("uid", user.getId());
            session.addAttribute("username", user.getUsername());
            session.addAttribute("email", user.getEmail());
            Map<String, Object> resp = new HashMap<>(2);
            resp.put("result", user);
            resp.put("msg", "登录成功");
            Succeed(resp);
        } catch (Exception e) {

            // log
            UserEntity userEntity = userService.getUserEntityByName(loginModel.username);
            if (userEntity != null) {
                userLogMQService.createUserLog(userEntity.getId(), userEntity.getUsername(), L_LOGIN, "", "failed", "", Request().getIp(), Request().getUa());
            }

            Fail(e);
        }
    }

    @HttpPost
    @Route("signup")
    public void register(@FromBody RegisterModel registerModel, @QueryParam("ref") String ref) {

        try{

            User user = userService.signup(registerModel);

            // 添加变更用户财富的任务至消息队列
            balanceMQService.increaseBalance(user.getId(), CoinRewards.R_REGISTER, B_REGISTER, "注册获得初始财富奖励", "");

            // log
            userLogMQService.createUserLog(user.getId(), user.getUsername(), L_REGISTER, "", "signuped", "", Request().getIp(), Request().getUa());

            if (ref != null && StringUtil.isNumberic(ref)) {

                String userLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/u/").concat(user.getUsername());

                // 给推广和被推广用户增加10个银币
                long refUid = Long.valueOf(ref);
                UserEntity refUser = userService.getUserEntity(refUid);
                if (refUser != null && !(refUser.getStatus().equals(Byte.valueOf("0")))) {
                    String refUserLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/u/").concat(refUser.getUsername());
                    balanceMQService.increaseBalance(refUid, CoinRewards.R_REGISTER_REF, B_REGISTER_REF, "推广用户".concat(user.getUsername()).concat("注册"), userLink);
                    balanceMQService.increaseBalance(user.getId(), CoinRewards.R_REGISTER_REF, B_REGISTER_BE_REF, "由用户".concat(refUser.getUsername()).concat("推荐注册，获得同等奖励"), refUserLink);
                }
            }

            Session session = Request().session();
            session.addAttribute("uid", user.getId());
            session.addAttribute("username", user.getUsername());
            session.addAttribute("email", user.getEmail());
            Map<String, Object> resp = new HashMap<>(2);
            resp.put("result", user);
            resp.put("msg", "注册成功, 请检查你的邮箱并点击激活链接完成账户激活");

            Succeed(resp);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("signout")
    public void logout() {

        try{

            Session session = Request().session();

            Object uid = session.attribute("uid");
            String username = session.attribute("username");
            session.clearAttributes();

            if (uid != null) {
                // log
                userLogMQService.createUserLog((long)uid, username, L_LOGOUT, "", "loggedOut", "", Request().getIp(), Request().getUa());
            }

            Succeed("注销成功");
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("activate")
    public void activate(@QueryParam("token") String token) {

        try {

            long uid = userService.activate(token);
            if (uid > 0) {
                // log
                userLogMQService.createUserLog(uid, "", L_ACTIVATE_ACCOUNT, "", "activated", "", Request().getIp(), Request().getUa());
            }

            Succeed(uid > 0);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("reactivate")
    public void reActivate(@FromBody ReActivationModel reActivationModel) {

        try {

            long uid = userService.reActivate(reActivationModel.email);

            if (uid > 0) {
                // log
                userLogMQService.createUserLog(uid, "", L_REACTIVATE_EMAIL, "", "", "", Request().getIp(), Request().getUa());
            }

            Succeed(uid > 0);
        } catch (Exception e) {

            Fail(e);
        }
    }
}
