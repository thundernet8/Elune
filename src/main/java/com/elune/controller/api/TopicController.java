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

import com.elune.utils.DateUtil;
import com.elune.utils.StringUtil;
import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.*;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.Session;

import java.util.*;

/**
 * @author Touchumind
 */
@RoutePrefix("api/v1/topics")
public class TopicController extends APIController {

    @FromService
    private TopicService topicService;

    @FromService
    private UserService userService;

    @FromService
    private TopicViewMQService topicViewMQService;

    @FromService
    private UserMetaService userMetaService;

    @FromService
    private TagService tagService;

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

            // 创建标签
            List<TagCreationModel> tags = StringUtil.getTagsFromContent(topicCreationModel.content, 5);
            tagService.createTags(tags, createResult);

            Map<String, Object> resp = new HashMap<>(2);
            resp.put("result", createResult);
            resp.put("msg", "话题创建成功");
            Succeed(resp);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("{long:id}")
    public void updateTopic(long id, @FromBody TopicUpdateModel topicUpdateModel) {

        topicUpdateModel.id = id;

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
            if (uid < 1) {

                throw new HttpException("你必须登录才能更新话题", 401);
            }

            UserEntity user = userService.getUserEntity(uid);

            if (user == null || user.getStatus().equals(Byte.valueOf("0")) || user.getRoleId() > 10) {

                throw new HttpException("你没有权限更新话题", 403);
            }

            Topic topic = topicService.getTopic(id);
            if (topic == null || topic.getStatus().equals(Byte.valueOf("0"))) {

                throw new Exception("话题不存在");
            }

            if (user.getRoleId() > 2 && DateUtil.getTimeStamp() - topic.getCreateTime() > 3600) {

                throw new Exception("话题创建超过1小时，不能重新编辑");
            }

            boolean updateResult = topicService.updateTopic(topicUpdateModel);

            if (updateResult) {

                Map<String, Object> resp = new HashMap<>(2);
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
    @Route("{long:id}")
    public void getTopic(long id) {

        try {

            topicViewMQService.increaseViews(id);
        } catch (Exception e) {
            // ignore
        }

        try {

            Topic topic = topicService.getTopic(id);
            Succeed(topic);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpGet
    @Route("")
    public void getTopics(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, @QueryParam("channelId") String channelId, @QueryParam("authorId") String authorId, @QueryParam("order") String order, @QueryParam("orderBy") String orderBy) {

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
                if (orderBy.equals("posts_count")) {

                    sb.append(",views_count ");
                    sb.append(order);
                }
                if (orderBy.equals("views_count")) {

                    sb.append(",posts_count ");
                    sb.append(order);
                }
                break;
            default:
                sb.append("id ");
                sb.append(order);
        }

        String orderClause = sb.toString();

        try {

            Pagination<Topic> pagination;

            if (authorId != null){
                pagination = topicService.getUserTopics(page, pageSize, Long.valueOf(authorId), orderClause);
            } else if (channelId != null) {
                pagination = topicService.getChannelTopics(page, pageSize, Integer.valueOf(channelId), orderClause);
            } else {
                pagination = topicService.getTopics(page, pageSize, orderClause);
            }
            Succeed(pagination);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("{long:id}/favorites")
    public void favoriteTopic(long id) {

        Session session = Request().session();
        long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
        if (uid < 1) {

            throw new HttpException("你必须登录才能收藏话题", 401);
        }

        try {

            boolean result = userMetaService.favoriteTopic(uid, id) && topicService.favoriteTopic(id);
            Succeed(result);
        } catch (Exception e) {
            Fail(e);
        }
    }

    @HttpDelete
    @Route("{long:id}/favorites")
    public void unFavoriteTopic(long id) {

        Session session = Request().session();
        long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
        if (uid < 1) {

            throw new HttpException("你必须登录才能取消收藏话题", 401);
        }

        try {

            boolean result = userMetaService.unfavoriteTopic(uid, id) && topicService.unfavoriteTopic(id);
            Succeed(result);
        } catch (Exception e) {
            Fail(e);
        }
    }

    @HttpPost
    @Route("{long:id}/likes")
    public void likeTopic(long id) {

        Session session = Request().session();
        long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
        if (uid < 1) {

            throw new HttpException("你必须登录才能点赞话题", 401);
        }

        try {

            boolean result = topicService.upvoteTopic(id);
            Succeed(result);
        } catch (Exception e) {
            Fail(e);
        }
    }

    @HttpDelete
    @Route("{long:id}/likes")
    public void unLikeTopic(long id) {

        Session session = Request().session();
        long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
        if (uid < 1) {

            throw new HttpException("你必须登录才能取消点赞话题", 401);
        }

        try {

            boolean result = topicService.cancelUpvoteTopic(id);
            Succeed(result);
        } catch (Exception e) {
            Fail(e);
        }
    }
}
