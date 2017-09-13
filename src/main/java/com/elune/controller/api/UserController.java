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

import com.elune.dal.DBManager;

import com.elune.model.User;
import com.elune.service.UserService;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.FromBody;
import com.fedepot.mvc.annotation.HttpPost;
import com.fedepot.mvc.annotation.Route;
import com.fedepot.mvc.annotation.RoutePrefix;
import com.fedepot.mvc.controller.Controller;

@RoutePrefix("api/users")
public class UserController extends Controller {

    private DBManager dbManager;

    @FromService
    private UserService userService;

    public UserController(DBManager dbManager) {

        this.dbManager = dbManager;
    }

    @Route("{int:id}")
    public String getUserDetail(int id) {

        User user = userService.getUser(id);

        String message;

        if (user == null) {

            message = "User with id " + id + " is not exist";
        } else {

            message = "User detail name " + user.nickname;
        }

        return message;
    }

    @HttpPost
    @Route("")
    public void addUser(@FromBody User user) {

        userService.createUser(user);
    }
}
