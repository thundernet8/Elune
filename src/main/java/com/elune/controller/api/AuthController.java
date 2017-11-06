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
    private NotificationService notificationService;

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
            user.setBalance(userMetaService.getBalance(uid));
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
            user.setBalance(userMetaService.getBalance(user.getId()));
            user.setDailySigned(userMetaService.hasSignedToday(user.getId()));

            // log
            userLogMQService.createUserLog(user.getId(), L_LOGIN, "", "loggedIn", "", Request().getIp(), Request().getUa());

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
                userLogMQService.createUserLog(userEntity.getId(), L_LOGIN, "", "failed", "", Request().getIp(), Request().getUa());
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
            balanceMQService.increaseBalance(user.getId(), CoinRewards.R_REGISTER);

            // log
            userLogMQService.createUserLog(user.getId(), L_REGISTER, "", "signuped", "", Request().getIp(), Request().getUa());

            if (ref != null && StringUtil.isNumberic(ref)) {

                // 给推广用户增加10个银币
                // TODO confirm user exist
                long refUid = Long.valueOf(ref);
                balanceMQService.increaseBalance(refUid, CoinRewards.R_REGISTER_REF);
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
            session.clearAttributes();

            if (uid != null) {
                // log
                userLogMQService.createUserLog((long)uid, L_LOGOUT, "", "loggedOut", "", Request().getIp(), Request().getUa());
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
                userLogMQService.createUserLog(uid, L_ACTIVATE_ACCOUNT, "", "activated", "", Request().getIp(), Request().getUa());
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
                userLogMQService.createUserLog(uid, L_REACTIVATE_EMAIL, "", "", "", Request().getIp(), Request().getUa());
            }

            Succeed(uid > 0);
        } catch (Exception e) {

            Fail(e);
        }
    }
}
