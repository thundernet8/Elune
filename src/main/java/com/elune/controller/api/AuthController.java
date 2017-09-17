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

import com.elune.model.LoginModel;
import com.elune.model.LoginUser;
import com.elune.model.RegisterModel;
import com.elune.model.User;
import com.elune.service.UserService;

import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.FromBody;
import com.fedepot.mvc.annotation.HttpPost;
import com.fedepot.mvc.annotation.Route;
import com.fedepot.mvc.annotation.RoutePrefix;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.Session;

import java.util.HashMap;
import java.util.Map;

// TODO csrf header verify
@RoutePrefix("api")
public class AuthController extends APIController{

    @FromService
    private UserService userService;

    @HttpPost
    @Route("user/me")
    public void checkMe() {

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
            if (uid < 1) {

                throw new HttpException("尚未登录", 200);
            }

            User user = userService.getUser(uid);

            session.addAttribute("uid", user.id);
            session.addAttribute("username", user.username);
            session.addAttribute("email", user.email);
            Map<String, Object> resp = new HashMap<>();
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
            Session session = Request().session();
            session.addAttribute("uid", user.id);
            session.addAttribute("username", user.username);
            session.addAttribute("email", user.email);
            Map<String, Object> resp = new HashMap<>();
            resp.put("result", user);
            resp.put("msg", "登录成功");
            Succeed(resp);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("signup")
    public void register(@FromBody RegisterModel registerModel) {

        try{

            User user = userService.signup(registerModel);
            Map<String, Object> resp = new HashMap<>();
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
            session.attributes().clear();
            Succeed("注销成功");
        } catch (Exception e) {

            Fail(e);
        }
    }
}
