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
import com.elune.entity.TopicEntity;
import com.elune.entity.UserEntity;
import com.elune.model.*;
import com.elune.service.*;
import com.elune.constants.CoinRewards;

import com.elune.utils.DateUtil;
import com.elune.utils.StringUtil;
import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.*;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.Session;

import java.util.*;

import static com.elune.constants.UserLogType.*;
import static com.elune.constants.NotificationType.*;
import static com.elune.constants.BalanceLogType.*;
import static com.elune.constants.CoinRewards.*;
import static com.elune.constants.RoleType.*;

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

    @FromService
    private UserLogMQService userLogMQService;

    @FromService
    private NotificationMQService notificationMQService;

    @FromService
    private BalanceMQService balanceMQService;

    @FromService
    private BalanceLogService balanceLogService;

    @FromService
    private AppConfiguration appConfiguration;
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

            // 尝试扣取积分
            int balance = balanceLogService.getBalance(uid);
            if (balance + R_CREATE_TOPIC < 0) {

                throw new HttpException("你没有足够的余额创建话题", 400);
            }

            long createResult = topicService.createTopic(author, topicCreationModel);

            if (createResult < 1) {
                throw new HttpException("创建话题失败", 400);
            }

            // 创建标签
            List<TagCreationModel> tags = StringUtil.getTagsFromContent(topicCreationModel.content, 5);
            tagService.createTags(tags, createResult);

            // log
            String topicLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/topic/").concat(Long.toString(createResult));
            userLogMQService.createUserLog(uid, author.getUsername(), L_CREATE_TOPIC, "", "创建了话题《".concat(topicCreationModel.title).concat("》"), topicLink, Request().getIp(), Request().getUa());
            // 扣除积分
            balanceMQService.increaseBalance(author.getId(), R_CREATE_TOPIC, B_CREATE_TOPIC, "创建话题《".concat(topicCreationModel.title).concat("》"), topicLink);

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

            if (user == null || user.getStatus().equals(Byte.valueOf("0")) || user.getRoleId() > NORMAL_USER) {

                throw new HttpException("你没有权限更新话题", 403);
            }

            TopicEntity topicEntity = topicService.getTopicEntity(id);
            if (topicEntity == null || topicEntity.getStatus().equals(Byte.valueOf("0"))) {

                throw new Exception("话题不存在");
            }

            if (user.getRoleId() > 2 && DateUtil.getTimeStamp() - topicEntity.getCreateTime() > 3600) {

                throw new Exception("话题创建超过1小时，不能重新编辑");
            }

            boolean updateResult = topicService.updateTopic(topicUpdateModel);

            if (updateResult) {
                String topicLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/topic/").concat(Long.toString(topicEntity.getId()));
                // log
                userLogMQService.createUserLog(uid, user.getUsername(), L_UPDATE_TOPIC, "", "更新了话题《".concat(topicEntity.getTitle()).concat("》"), topicLink, Request().getIp(), Request().getUa());

                // notification
                if (uid != topicEntity.getAuthorId()) {

                    notificationMQService.createNotification(user.getUsername(), topicEntity.getAuthorName(), user.getUsername().concat("更新了话题《").concat(topicEntity.getTitle()).concat("》"), "", N_TOPIC_BE_UPDATED);
                }

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

        UserEntity user = userService.getUserEntity(uid);

        if (user == null) {

            throw new HttpException("你必须登录才能收藏话题", 401);
        }

        if (user.getStatus().equals(Byte.valueOf("0"))) {

            throw new HttpException("你没有权限收藏话题(账户未激活或已禁用)", 403);
        }

        TopicEntity topicEntity = topicService.getTopicEntity(id);
        if (topicEntity == null || topicEntity.getStatus().equals(Byte.valueOf("0"))) {

            throw new HttpException("话题不存在或已被删除", 404);
        }

        try {

            boolean result = userMetaService.favoriteTopic(uid, id) && topicService.favoriteTopic(id);

            if (uid != topicEntity.getAuthorId()) {
                // log
                String topicLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/topic/").concat(Long.toString(topicEntity.getId()));
                userLogMQService.createUserLog(uid, user.getUsername(), L_FAVORITE_TOPIC, "", "收藏了话题《".concat(topicEntity.getTitle()).concat("》"), topicLink,  Request().getIp(), Request().getUa());

                // notification
                notificationMQService.createNotification(user.getUsername(), topicEntity.getAuthorName(), user.getUsername().concat("收藏了你的话题《".concat(topicEntity.getTitle()).concat("》")), "", N_TOPIC_FAVORITE);

                // add balance for author
                balanceMQService.increaseBalance(topicEntity.getAuthorId(), CoinRewards.R_TOPIC_BE_FAVORITED, B_TOPIC_BE_FAVORITED, "创建的话题《".concat(topicEntity.getTitle()).concat("》被").concat(user.getUsername()).concat("收藏"), topicLink);
            }

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

        UserEntity user = userService.getUserEntity(uid);

        if (user == null) {

            throw new HttpException("你必须登录才能取消收藏话题", 401);
        }

        if (user.getStatus().equals(Byte.valueOf("0"))) {

            throw new HttpException("你没有权限取消收藏话题(账户未激活或已禁用)", 403);
        }

        TopicEntity topicEntity = topicService.getTopicEntity(id);
        if (topicEntity == null || topicEntity.getStatus().equals(Byte.valueOf("0"))) {

            throw new HttpException("话题不存在或已被删除", 404);
        }

        try {

            boolean result = userMetaService.unfavoriteTopic(uid, id) && topicService.unfavoriteTopic(id);

            if (uid != topicEntity.getAuthorId()) {

                // log
                String topicLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/topic/").concat(Long.toString(topicEntity.getId()));
                userLogMQService.createUserLog(uid, user.getUsername(), L_UNFAVORITE_TOPIC, "", "取消收藏话题《".concat(topicEntity.getTitle()).concat("》"), topicLink,  Request().getIp(), Request().getUa());

                // notification
                notificationMQService.createNotification(user.getUsername(), topicEntity.getAuthorName(), user.getUsername().concat("取消收藏了你的话题《".concat(topicEntity.getTitle()).concat("》")), "", N_TOPIC_UNFAVORITE);

                // cancel balance rewards for author
                balanceMQService.decreaseBalance(topicEntity.getAuthorId(), CoinRewards.R_TOPIC_BE_FAVORITED, B_TOPIC_BE_CANCEL_FAVORITE, "创建的话题《".concat(topicEntity.getTitle()).concat("》被").concat(user.getUsername()).concat("取消收藏，回收奖励"), topicLink);
            }

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

        UserEntity user = userService.getUserEntity(uid);

        if (user == null) {

            throw new HttpException("你必须登录才能点赞话题", 401);
        }

        if (user.getStatus().equals(Byte.valueOf("0"))) {

            throw new HttpException("你没有权限点赞话题(账户未激活或已禁用)", 403);
        }

        // 尝试扣取积分
        int balance = balanceLogService.getBalance(uid);
        if (balance + R_LIKE_TOPIC < 0) {

            throw new HttpException("你没有足够的余额点赞话题", 400);
        }

        TopicEntity topicEntity = topicService.getTopicEntity(id);
        if (topicEntity == null || topicEntity.getStatus().equals(Byte.valueOf("0"))) {

            throw new HttpException("话题不存在或已被删除", 404);
        }

        try {

            boolean result = topicService.upvoteTopic(id);
            if (!result) {
                throw new HttpException("点赞话题失败", 400);
            }

            String topicLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/topic/").concat(Long.toString(topicEntity.getId()));

            // 扣除积分
            balanceMQService.increaseBalance(user.getId(), R_LIKE_TOPIC, B_LIKE_TOPIC, "感谢话题《".concat(topicEntity.getTitle()).concat("》"), topicLink);

            if (uid != topicEntity.getAuthorId()) {
                // log
                userLogMQService.createUserLog(uid, user.getUsername(), L_LIKE_TOPIC, "", "喜欢了话题《".concat(topicEntity.getTitle()).concat("》"), topicLink, Request().getIp(), Request().getUa());

                // notification
                notificationMQService.createNotification(user.getUsername(), topicEntity.getAuthorName(), user.getUsername().concat("喜欢了你的话题《".concat(topicEntity.getTitle()).concat("》")), "", N_TOPIC_LIKE);

                // add balance for author
                balanceMQService.increaseBalance(topicEntity.getAuthorId(), CoinRewards.R_TOPIC_BE_LIKED, B_TOPIC_BE_LIKED, "创建的话题《".concat(topicEntity.getTitle()).concat("》被").concat(user.getUsername()).concat("点赞"), topicLink);
            }

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

        UserEntity user = userService.getUserEntity(uid);

        if (user == null) {

            throw new HttpException("你必须登录才能点赞话题", 401);
        }

        if (user.getStatus().equals(Byte.valueOf("0"))) {

            throw new HttpException("你没有权限点赞话题(账户未激活或已禁用)", 403);
        }

        TopicEntity topicEntity = topicService.getTopicEntity(id);
        if (topicEntity == null || topicEntity.getStatus().equals(Byte.valueOf("0"))) {

            throw new HttpException("话题不存在或已被删除", 404);
        }

        try {

            boolean result = topicService.cancelUpvoteTopic(id);

            if (uid != topicEntity.getAuthorId()) {
                // log
                String topicLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/topic/").concat(Long.toString(topicEntity.getId()));
                userLogMQService.createUserLog(uid, user.getUsername(), L_CANCEL_LIKE_TOPIC, "", "取消点赞话题《".concat(topicEntity.getTitle()).concat("》"), topicLink, Request().getIp(), Request().getUa());

                // notification
                notificationMQService.createNotification(user.getUsername(), topicEntity.getAuthorName(), user.getUsername().concat("取消对你的话题《".concat(topicEntity.getTitle()).concat("》的点赞")), "", N_TOPIC_UNLIKE);

                // cancel balance rewards for author
                balanceMQService.decreaseBalance(topicEntity.getAuthorId(), CoinRewards.R_TOPIC_BE_LIKED, B_TOPIC_BE_CANCEL_LIKE, "创建的话题《".concat(topicEntity.getTitle()).concat("》被").concat(user.getUsername()).concat("取消点赞，回收奖励"), topicLink);
            }

            Succeed(result);
        } catch (Exception e) {
            Fail(e);
        }
    }

    @HttpPost
    @Route("{long:id}/sticky")
    public void stickyTopic(long id) {

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
            if (uid < 1) {

                throw new HttpException("你必须登录才能置顶话题", 401);
            }

            UserEntity user = userService.getUserEntity(uid);

            if (user == null || user.getStatus().equals(Byte.valueOf("0")) || user.getRoleId() > ADMIN) {

                throw new HttpException("你没有权限置顶话题", 403);
            }

            TopicEntity topicEntity = topicService.getTopicEntity(id);
            if (topicEntity == null || topicEntity.getStatus().equals(Byte.valueOf("0"))) {

                throw new Exception("话题不存在");
            }

            boolean updateResult = topicService.pinTopic(id);

            if (updateResult) {
                String topicLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/topic/").concat(Long.toString(topicEntity.getId()));
                // log
                userLogMQService.createUserLog(uid, user.getUsername(), L_STICKY_TOPIC, "", "置顶了话题《".concat(topicEntity.getTitle()).concat("》"), topicLink, Request().getIp(), Request().getUa());

                // notification
                if (uid != topicEntity.getAuthorId()) {
                    notificationMQService.createNotification(topicEntity.getAuthorName(), user.getUsername().concat("置顶了你的话题《").concat(topicEntity.getTitle()).concat("》"), "", N_TOPIC_BE_STICKY);
                }

                // add balance for author
                balanceMQService.increaseBalance(topicEntity.getAuthorId(), CoinRewards.R_TOPIC_BE_STICKY, B_TOPIC_BE_STICKY, "创建的话题《".concat(topicEntity.getTitle()).concat("》被").concat(user.getUsername()).concat("置顶"), topicLink);

                Succeed(updateResult);
            } else {

                throw new Exception("话题置顶失败");
            }
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpDelete
    @Route("{long:id}/sticky")
    public void unStickyTopic(long id) {

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
            if (uid < 1) {

                throw new HttpException("你必须登录才能取消置顶话题", 401);
            }

            UserEntity user = userService.getUserEntity(uid);

            if (user == null || user.getStatus().equals(Byte.valueOf("0")) || user.getRoleId() > ADMIN) {

                throw new HttpException("你没有权限取消置顶话题", 403);
            }

            TopicEntity topicEntity = topicService.getTopicEntity(id);
            if (topicEntity == null || topicEntity.getStatus().equals(Byte.valueOf("0"))) {

                throw new Exception("话题不存在");
            }

            boolean updateResult = topicService.unpinTopic(id);

            if (updateResult) {
                String topicLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/topic/").concat(Long.toString(topicEntity.getId()));
                // log
                userLogMQService.createUserLog(uid, user.getUsername(), L_UNSTICKY_TOPIC, "", "取消置顶话题《".concat(topicEntity.getTitle()).concat("》"), topicLink, Request().getIp(), Request().getUa());

                // notification
                if (uid != topicEntity.getAuthorId()) {
                    notificationMQService.createNotification(topicEntity.getAuthorName(), user.getUsername().concat("取消置顶你的话题《").concat(topicEntity.getTitle()).concat("》"), "", N_TOPIC_BE_CANCEL_STICKY);
                }

                Succeed(updateResult);
            } else {

                throw new Exception("话题取消置顶失败");
            }
        } catch (Exception e) {

            Fail(e);
        }
    }
}
