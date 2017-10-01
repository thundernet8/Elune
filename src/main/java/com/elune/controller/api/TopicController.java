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
import com.elune.model.Pagination;
import com.elune.model.Topic;
import com.elune.model.TopicCreationModel;
import com.elune.model.TopicUpdateModel;
import com.elune.service.TopicService;

import com.elune.service.UserService;
import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.*;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RoutePrefix("api/v1/topics")
public class TopicController extends APIController {

    @FromService
    private TopicService topicService;

    @FromService
    private UserService userService;

    @HttpPost
    @Route("")
    public void createTopic(@FromBody TopicCreationModel topicCreationModel) {

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
            if (uid < 1) {

                throw new HttpException("你必须登录才能创建话题", 401);
            }

            UserEntity author = userService.getUserEntity(uid);

            if (author == null) {

                throw new HttpException("你必须登录才能创建话题", 401);
            }

            if (author.getStatus() != 1) {

                throw new HttpException("你没有权限创建话题(账户未激活或已禁用)", 403);
            }

            long createResult = topicService.createTopic(author, topicCreationModel);

            Map<String, Object> resp = new HashMap<>();
            resp.put("result", createResult);
            resp.put("msg", "话题创建成功");
            Succeed(resp);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPut
    @Route("{int:id}")
    public void updateTopic(long id, @FromBody TopicUpdateModel topicUpdateModel) {

        topicUpdateModel.id = id;

        try {

            boolean updateResult = topicService.updateTopic(topicUpdateModel);

            if (updateResult) {

                Map<String, Object> resp = new HashMap<>();
                resp.put("result", true);
                resp.put("msg", "话题更新成功");
                Succeed(resp);
            } else {

                throw new Exception("话题更新失败");
            }
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpGet
    @Route("")
    public void getTopics(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, @QueryParam("order") String order, @QueryParam("orderBy") String orderBy) {

        if (order == null || !(order.toLowerCase().equals("asc"))) {

            order = "DESC";
        }

        if (orderBy == null) {

            orderBy = "id";
        }

        StringBuilder sb = new StringBuilder();
        switch (orderBy) {

            case "update_time":
            case "post_time":
            case "posts_count":
            case "views_count":
                sb.append(orderBy);
                sb.append(" ");
                sb.append(order);
                sb.append(",");
                sb.append("id ");
                sb.append(order);
                break;
            default:
                sb.append("id ");
                sb.append(order);
        }

        String orderClause = sb.toString();

        try {

            Pagination<Topic> pagination = topicService.getTopics(page, pageSize, orderClause);
            Succeed(pagination);
        } catch (Exception e) {

            Fail(e);
        }
    }
}
