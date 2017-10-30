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

import com.elune.entity.TopicEntity;
import com.elune.entity.UserEntity;
import com.elune.model.Pagination;
import com.elune.model.Post;
import com.elune.model.PostCreationModel;
import com.elune.service.BalanceMQService;
import com.elune.service.PostService;
import com.elune.service.TopicService;
import com.elune.service.UserService;

import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.*;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.Session;

import java.util.HashMap;
import java.util.Map;

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

    @HttpPost
    @Route("")
    public void createPost(@FromBody PostCreationModel postCreationModel) {

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
            if (uid < 1) {

                throw new HttpException("你必须登录才能评论或回复", 401);
            }

            UserEntity author = userService.getUserEntity(uid);

            if (author == null) {

                throw new HttpException("你必须登录才能评论或回复", 401);
            }

            if (author.getStatus() != 1) {

                throw new HttpException("你没有权限创建评论或回复(账户未激活或已禁用)", 403);
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

            long createResult = postService.createPost(author, postCreationModel);

            // 给主题作者增加铜币

            if (!author.getId().equals(topicEntity.getAuthorId())) {

                balanceMQService.increaseBalance(uid, 10);
            }

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

        StringBuilder sb = new StringBuilder();
        sb.append(orderBy);
        sb.append(" ");
        sb.append(order);

        String orderClause = sb.toString();

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
}
