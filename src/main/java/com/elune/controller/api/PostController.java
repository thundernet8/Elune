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
import com.elune.entity.PostEntity;
import com.elune.entity.TopicEntity;
import com.elune.entity.UserEntity;
import com.elune.model.Pagination;
import com.elune.model.Post;
import com.elune.model.PostCreationModel;
import com.elune.service.*;
import com.elune.constants.CoinRewards;

import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.*;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.Session;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.elune.constants.UserLogType.*;
import static com.elune.constants.NotificationType.*;
import static com.elune.constants.BalanceLogType.*;
import static com.elune.constants.CoinRewards.*;

/**
 * @author Touchumind
 */
@RoutePrefix("api/v1/posts")
public class PostController extends APIController {

    @FromService
    private TopicService topicService;

    @FromService
    private PostService postService;

    @FromService
    private UserService userService;

    @FromService
    private BalanceMQService balanceMQService;

    @FromService
    private BalanceLogService balanceLogService;

    @FromService
    private UserLogMQService userLogMQService;

    @FromService
    private NotificationMQService notificationMQService;

    @FromService
    private TopicActivityNoticeMQService topicActivityNoticeMQService;

    @FromService
    private AppConfiguration appConfiguration;

    @HttpPost
    @Route("")
    public void createPost(@FromBody PostCreationModel postCreationModel) {

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
            if (uid < 1) {

                throw new HttpException("你必须登录才能评论或回复", 401);
            }

            UserEntity user = userService.getUserEntity(uid);

            if (user == null) {

                throw new HttpException("你必须登录才能评论或回复", 401);
            }

            if (user.getStatus().equals(Byte.valueOf("0"))) {

                throw new HttpException("你没有权限创建评论或回复(账户未激活或已禁用)", 403);
            }

            // 尝试扣取积分
            int balance = balanceLogService.getBalance(uid);
            if (balance + R_CREATE_POST < 0) {

                throw new HttpException("你没有足够的余额创建回复", 400);
            }

            TopicEntity topicEntity = topicService.getTopicEntity(postCreationModel.topicId);

            if (topicEntity.getStatus().equals(Byte.valueOf("0"))) {

                throw new HttpException("话题不存在或已被删除，无法发表评论", 400);
            }

            if (topicEntity.getCommentStatus().equals(Byte.valueOf("0"))) {

                throw new HttpException("话题已禁止评论", 400);
            }

            postCreationModel.ip = Request().getIp();
            postCreationModel.ua = Request().getUa();

            long createResult = postService.createPost(user, postCreationModel);

            if (createResult < 1) {
                throw new HttpException("创建回复失败", 400);
            }

            String topicLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/topic/").concat(Long.toString(topicEntity.getId()));
            // 扣除积分
            balanceMQService.increaseBalance(user.getId(), R_CREATE_POST, B_CREATE_POST, "在话题《".concat(topicEntity.getTitle()).concat("》上发表了回复"), topicLink);

            // 给主题作者增加铜币
            if (!(user.getId().equals(topicEntity.getAuthorId()))) {
                balanceMQService.increaseBalance(topicEntity.getAuthorId(), CoinRewards.R_TOPIC_BE_REPLIED, B_TOPIC_BE_REPLIED, "创建的话题《".concat(topicEntity.getTitle()).concat("》收到来自").concat(user.getUsername()).concat("的回复"), topicLink);
            }

            // log
            userLogMQService.createUserLog(uid, user.getUsername(), L_CREATE_POST, "", "在话题《".concat(topicEntity.getTitle()).concat("》上创建了新回复: ").concat(postCreationModel.content), topicLink, Request().getIp(), Request().getUa());

            // notification
            notificationMQService.createNotification(user.getUsername(), topicEntity.getAuthorName(), user.getUsername().concat("在你的话题《".concat(topicEntity.getTitle()).concat("》发表了回复")), postCreationModel.content, N_TOPIC_REPLY);

            // 话题关注者通知
            topicActivityNoticeMQService.noticeTopicFollowers(topicEntity.getId(), user.getUsername().concat("在你关注的话题《".concat(topicEntity.getTitle()).concat("》上发表了回复")), postCreationModel.content, N_TOPIC_REPLY);

            Arrays.stream(postCreationModel.mentions).forEach(mention -> {
                notificationMQService.createNotification(user.getUsername(), mention, user.getUsername().concat("在话题《".concat(topicEntity.getTitle()).concat("》的评论中@了你")), postCreationModel.content, N_AT);
            });

            Map<String, Object> resp = new HashMap<>(2);
            resp.put("result", createResult);
            resp.put("msg", "评论或回复成功");
            Succeed(resp);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpGet
    @Route("")
    public void getPosts(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, @QueryParam("topicId") String topicId, @QueryParam("authorId") String authorId, @QueryParam("order") String order, @QueryParam("orderBy") String orderBy) {

        if (order == null || !(order.toLowerCase().equals("asc"))) {

            order = "DESC";
        }

        if (orderBy == null) {

            orderBy = "id";
        }

        String orderClause = orderBy.concat(" ").concat(order);

        try {

            Pagination<Post> pagination;
            if (authorId != null) {
                pagination = postService.getUserPosts(page, pageSize, Long.valueOf(authorId), orderClause);
            } else if (topicId != null) {
                pagination = postService.getTopicPosts(page, pageSize, Integer.valueOf(topicId), orderClause);
            } else {
                pagination = postService.getPosts(page, pageSize, orderClause);
            }
            Succeed(pagination);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("{long:id}/likes")
    public void likePost(long id) {

        Session session = Request().session();
        long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
        if (uid < 1) {

            throw new HttpException("你必须登录才能点赞评论", 401);
        }

        UserEntity user = userService.getUserEntity(uid);

        if (user == null) {

            throw new HttpException("你必须登录才能点赞评论", 401);
        }

        if (user.getStatus().equals(Byte.valueOf("0"))) {

            throw new HttpException("你没有权限点赞评论(账户未激活或已禁用)", 403);
        }

        // 尝试扣取积分
        int balance = balanceLogService.getBalance(uid);
        if (balance + R_LIKE_POST < 0) {

            throw new HttpException("你没有足够的余额点赞评论", 400);
        }

        PostEntity postEntity = postService.getPostEntity(id);
        if (postEntity == null || postEntity.getStatus().equals(Byte.valueOf("0"))) {

            throw new HttpException("评论不存在或已被删除", 404);
        }

        if (postEntity.getAuthorId().equals(uid)) {

            throw new HttpException("不能感谢自己", 400);
        }
        TopicEntity topicEntity = topicService.getTopicEntity(postEntity.getTid());

        try {

            boolean result = postService.upvotePost(id);
            if (!result) {
                throw new HttpException("点赞话题失败", 400);
            }

            String topicLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/topic/").concat(Long.toString(topicEntity.getId()));

            // 扣除积分
            balanceMQService.increaseBalance(user.getId(), R_LIKE_POST, B_LIKE_POST, "感谢".concat(postEntity.getAuthorName()).concat("在话题《").concat(topicEntity.getTitle()).concat("》的评论"), topicLink);

            if (uid != postEntity.getAuthorId()) {
                // log
                userLogMQService.createUserLog(uid, user.getUsername(), L_LIKE_POST, "", "感谢了".concat(postEntity.getAuthorName()).concat("在话题《").concat(topicEntity.getTitle()).concat("》上的评论"), topicLink, Request().getIp(), Request().getUa());

                // notification
                notificationMQService.createNotification(postEntity.getAuthorName(), user.getUsername().concat("感谢了").concat("你在话题《").concat(topicEntity.getTitle()).concat("》上的评论"), "", N_POST_LIKE);

                // add balance for author
                balanceMQService.increaseBalance(postEntity.getAuthorId(), CoinRewards.R_POST_BE_LIKED, B_POST_BE_LIKED, "在话题《".concat(topicEntity.getTitle()).concat("》上的评论收到").concat(user.getUsername()).concat("的感谢"), topicLink);
            }

            Succeed(result);
        } catch (Exception e) {
            Fail(e);
        }
    }
}
