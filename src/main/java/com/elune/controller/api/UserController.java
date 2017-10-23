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
import com.elune.model.*;
import com.elune.service.PostService;
import com.elune.service.TopicService;
import com.elune.service.UserService;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.FromBody;
import com.fedepot.mvc.annotation.HttpPost;
import com.fedepot.mvc.annotation.Route;
import com.fedepot.mvc.annotation.RoutePrefix;
import com.fedepot.mvc.controller.APIController;

@RoutePrefix("api/v1/users")
public class UserController extends APIController {

    private DBManager dbManager;

    @FromService
    private UserService userService;

    @FromService
    private TopicService topicService;

    @FromService
    private PostService postService;

    public UserController(DBManager dbManager) {

        this.dbManager = dbManager;
    }

    @HttpPost
    @Route("{long:id}")
    public void getUserDetail(long id) {

        try {

            User user = userService.getUser(id);
            Succeed(user);
        } catch (Exception e) {

            Fail(e);
        }

    }

    @HttpPost
    @Route("name")
    public void getNamedUser(@FromBody NamedUserFetchModel namedUserFetchModel) {

        try {

            NamedUser namedUser = userService.getNamedUser(namedUserFetchModel.username);
            namedUser.setTopicsCount(topicService.countTopicsByAuthor(namedUser.getId()));
            namedUser.setPostsCount(postService.countPostsByAuthor(namedUser.getId()));
            namedUser.setEmail("");
            Succeed(namedUser);
        } catch (Exception e) {

            Fail(e);
        }
    }
}
