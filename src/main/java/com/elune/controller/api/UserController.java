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
import com.elune.entity.UsermetaEntity;
import com.elune.model.*;
import com.elune.service.*;

import com.elune.utils.DateUtil;
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
import java.util.Random;

/**
 * @author Touchumind
 */
@RoutePrefix("api/v1/users")
public class UserController extends APIController {

    private DBManager dbManager;

    @FromService
    private UserMetaService userMetaService;

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
            namedUser.setFavoritesCount(userMetaService.countFavorites(namedUser.getId()));
            namedUser.setEmail("");
            Succeed(namedUser);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("profile")
    public void updateUserProfile(@FromBody UserProfileSetting userProfileSetting) {

        Session session = Request().session();
        long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
        if (uid < 1) {

            throw new HttpException("尚未登录, 不能更新资料", 401);
        }

        try {

            Map<String, Object> updateInfo = new HashMap<>(4);
            updateInfo.put("id", uid);
            updateInfo.put("nickname", userProfileSetting.nickname);
            updateInfo.put("url", userProfileSetting.url);
            updateInfo.put("bio", userProfileSetting.bio);
            Succeed(userService.updateInfo(updateInfo));
        } catch (Exception e) {
            Fail(e);
        }
    }

    @HttpPost
    @Route("dailySign")
    public void dailySign() {

        Session session = Request().session();
        long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
        if (uid < 1) {

            throw new HttpException("尚未登录, 不能签到", 401);
        }

        try {

            if (userMetaService.hasSignedToday(uid)) {

                throw new HttpException("今日已签到", 400);
            }

            UsermetaEntity balanceMeta = userMetaService.getSingleUsermeta(uid, "balance");
            int balance = balanceMeta != null ? Integer.valueOf(balanceMeta.getMetaValue()) : 0;
            Random random = new Random(DateUtil.getTimeStamp() % 50);

            int change = random.nextInt(50) + 1;
            int newBalance = balance + change;

            userMetaService.createOrUpdateUsermeta(uid, "balance", Integer.toString(newBalance));
            userMetaService.createOrUpdateUsermeta(uid, "dailySign", Integer.toString(DateUtil.getTimeStamp()));

            Map<String, Object> resp = new HashMap<>(2);
            resp.put("msg", "签到成功, 获得 " + newBalance + " 铜币");
            resp.put("result", change);
            Succeed(resp);
        } catch (Exception e) {

            Fail(e);
        }
    }
}
