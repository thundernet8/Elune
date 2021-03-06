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


package com.elune.service;

import com.elune.entity.TopicEntity;
import com.elune.entity.UserEntity;
import com.elune.model.*;

import java.util.List;

/**
 * @author Touchumind
 */
public interface TopicService {

    Topic getTopic(long id);

    TopicEntity getTopicEntity(long id);

    long createTopic(UserEntity author, TopicCreationModel topicCreationModel);

    boolean updateTopic(TopicUpdateModel topicUpdateModel);

    boolean pinTopic(long id);

    boolean unpinTopic(long id);

    boolean markTopicEssence(long id);

    boolean unmarkTopicEssence(long id);

    boolean updateTopicViews(long id);

    boolean updateTopicViews(long id, int count);

    boolean upvoteTopic(long id);

    boolean cancelUpvoteTopic(long id);

    boolean downvoteTopic(long id);

    boolean favoriteTopic(long id);

    boolean unfavoriteTopic(long id);

    boolean updateTopicPostsCount(long id);

    boolean lastReplayTopic(long id, UserEntity author);

    boolean updateTopicFactor(long id, int factor);

    boolean deleteTopic(long id);

    boolean toggleTopicComment(long id, boolean enable);

    long countTopicsByAuthor(long authorId);

    /**
     * 分页查询话题
     *
     * @param page 分页
     * @param pageSize 分页大小
     * @param orderClause 查询排序规则
     * @return 分页的话题列表对象
     */
    Pagination<Topic> getTopics(int page, int pageSize, String orderClause);

    Pagination<Topic> getChannelTopics(int page, int pageSize, int channelId, String orderClause);

    Pagination<Topic> getUserTopics(int page, int pageSize, long authorId, String orderClause);

    List<Topic> getTopicsByIdList(List<Long> ids);
}
