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

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.FromBody;
import com.fedepot.mvc.annotation.HttpPost;
import com.fedepot.mvc.annotation.Route;
import com.fedepot.mvc.annotation.RoutePrefix;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.Session;

// TODO csrf header verify
@RoutePrefix("api")
public class AuthController extends APIController{

    @FromService
    private UserService userService;

    @HttpPost
    @Route("signin")
    public void login(@FromBody LoginModel loginModel) {

        try {

            LoginUser user = userService.signin(loginModel);
            Succeed(user);
            // TODO session绑定
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("signup")
    public void register(@FromBody RegisterModel registerModel) {

        try{

            User user = userService.signup(registerModel);
            Succeed(user);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("signout")
    public void logout() {

        try{

            Session session = Request().session();
            long uid = (long)session.attribute("uid");
            userService.signout(uid);
            Succeed("");
        } catch (Exception e) {

            Fail(e);
        }
    }
}
