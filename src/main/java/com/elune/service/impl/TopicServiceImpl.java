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


package com.elune.service.impl;

import com.elune.dal.DBManager;
import com.elune.dao.TopicMapper;
import com.elune.entity.TopicEntityWithBLOBs;
import com.elune.model.*;
import com.elune.service.TopicService;
import com.elune.utils.DozerMapperUtil;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    @FromService
    private DBManager dbManager;

    @Override
    public Topic getTopic(long id) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
            TopicEntityWithBLOBs topicEntity = mapper.selectByPrimaryKey(id);
            if (topicEntity == null) {

                return null;
            }

            Topic topic = DozerMapperUtil.map(topicEntity, Topic.class);
            // TODO query author/channel/tags entities for topic

            return topic;
        }
    }

    @Override
    public int createTopic(TopicCreationModel topicCreationModel) {
        return 0;
    }

    @Override
    public boolean updateTopic(TopicCreationModel topicUpdateModel) {
        return false;
    }

    @Override
    public boolean pinTopic(long id) {
        return false;
    }

    @Override
    public boolean unpinTopic(long id) {
        return false;
    }

    @Override
    public boolean markTopicEssence(long id) {
        return false;
    }

    @Override
    public boolean unmarkTopicEssence(long id) {
        return false;
    }

    @Override
    public boolean upvoteTopic(long id) {
        return false;
    }

    @Override
    public boolean downvoteTopic(long id) {
        return false;
    }

    @Override
    public boolean favoriteTopic(long id) {
        return false;
    }

    @Override
    public boolean unfavoriteTopic(long id) {
        return false;
    }

    @Override
    public boolean updateTopicPostsCount(long id) {
        return false;
    }

    @Override
    public boolean udateTopicViews(long id) {
        return false;
    }

    @Override
    public boolean updateTopicFactor(long id, int factor) {
        return false;
    }

    @Override
    public boolean lastReplayTopic(long id) {
        return false;
    }

    @Override
    public boolean toggleTopicComment(long id, boolean enable) {
        return false;
    }

    @Override
    public boolean deleteTopic(long id) {
        return false;
    }

    @Override
    public Pagination<Topic> getLatestTopics(int page, int pageSize) {
        return null;
    }
}
