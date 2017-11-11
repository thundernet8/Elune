/**
 * Elune - Lightweight Forum Powered by Razor
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


package com.elune.service;

import com.elune.entity.PostEntity;
import com.elune.entity.UserEntity;
import com.elune.model.*;

/**
 * @author Touchumind
 */
public interface PostService {

    Post getPost(long id);

    PostEntity getPostEntity(long id);

    long createPost(UserEntity author, PostCreationModel postCreationModel);

    boolean upvotePost(long id);

    boolean downvotePost(long id);

    boolean deletePost(long id);

    long countPostsByAuthor(long authorId);

    /**
     * 分页查询话题
     *
     * @param page 分页
     * @param pageSize 分页大小
     * @param orderClause 查询排序规则
     * @return 分页的评论/回复列表对象
     */
    Pagination<Post> getPosts(int page, int pageSize, String orderClause);

    Pagination<Post> getTopicPosts(int page, int pageSize, long topicId, String orderClause);

    Pagination<Post> getUserPosts(int page, int pageSize, long authorId, String orderClause);
}
