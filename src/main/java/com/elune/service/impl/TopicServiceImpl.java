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
import com.elune.dao.ChannelMapper;
import com.elune.dao.TopicMapper;
import com.elune.entity.*;
import com.elune.model.*;
import com.elune.service.ChannelService;
import com.elune.service.TagService;
import com.elune.service.TopicService;
import com.elune.service.UserService;
import com.elune.utils.DateUtil;
import com.elune.utils.DozerMapperUtil;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    @FromService
    private DBManager dbManager;

    @FromService
    private ChannelService channelService;

    @FromService
    private TagService tagService;

    @FromService
    private UserService userService;

    @Override
    public Topic getTopic(long id) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
            TopicEntity topicEntity = mapper.selectByPrimaryKey(id);
            if (topicEntity == null) {

                return null;
            }

            return assembleTopics(Collections.singletonList(topicEntity)).get(0);

        }
    }

    @Override
    public long createTopic(UserEntity author, TopicCreationModel topicCreationModel) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);

            TopicEntity topicEntity = TopicEntity.builder().cid(topicCreationModel.channelId).title(topicCreationModel.title).authorName(author.getUsername()).authorId(author.getId()).content(topicCreationModel.content).contentHtml(topicCreationModel.contentHtml).contentRaw(topicCreationModel.contentRaw).createTime(DateUtil.getTimeStamp()).build();
            mapper.insertSelective(topicEntity);
            sqlSession.commit();

            channelService.updateTopicCount(topicCreationModel.channelId, 1);

            return topicEntity.getId();

        } catch (Exception e) {

            log.error("Insert topic failed", e);
            throw e;
        }
    }

    @Override
    public boolean updateTopic(TopicUpdateModel topicUpdateModel) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
            TopicEntity topicEntity = TopicEntity.builder().id(topicUpdateModel.id).title(topicUpdateModel.title).content(topicUpdateModel.content).contentHtml(topicUpdateModel.contentHtml).contentRaw(topicUpdateModel.contentRaw).updateTime(DateUtil.getTimeStamp()).build();
            int update = mapper.updateByPrimaryKeySelective(topicEntity);
            sqlSession.commit();

            return update > 0;
        }

    }

    @Override
    public boolean pinTopic(long id) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).isPinned(Byte.parseByte("1")).build();
        return updateTopic(topicEntity);
    }

    @Override
    public boolean unpinTopic(long id) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).isPinned(Byte.parseByte("0")).build();
        return updateTopic(topicEntity);
    }

    @Override
    public boolean markTopicEssence(long id) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).isEssence(Byte.parseByte("1")).build();
        return updateTopic(topicEntity);
    }

    @Override
    public boolean unmarkTopicEssence(long id) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).isEssence(Byte.parseByte("0")).build();
        return updateTopic(topicEntity);
    }

    @Override
    public boolean upvoteTopic(long id) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).upvotesCount(1).build();
        return increaseUpdateTopic(topicEntity);
    }

    @Override
    public boolean downvoteTopic(long id) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).downvotesCount(1).build();
        return increaseUpdateTopic(topicEntity);
    }

    @Override
    public boolean favoriteTopic(long id) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).favoritesCount(1).build();
        return increaseUpdateTopic(topicEntity);
    }

    @Override
    public boolean unfavoriteTopic(long id) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).favoritesCount(1).build();
        return decreaseUpdateTopic(topicEntity);
    }

    @Override
    public boolean updateTopicPostsCount(long id) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).postsCount(1).build();
        return increaseUpdateTopic(topicEntity);
    }

    @Override
    public boolean updateTopicViews(long id) {

        return updateTopicViews(id, 1);
    }

    @Override
    public boolean updateTopicViews(long id, int count) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).viewsCount(count).build();
        return increaseUpdateTopic(topicEntity);
    }

    @Override
    public boolean updateTopicFactor(long id, int factor) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).factor(factor).build();
        return updateTopic(topicEntity);
    }

    @Override
    public boolean lastReplayTopic(long id, UserEntity author) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).postTime(DateUtil.getTimeStamp()).poster(author.getUsername()).posterId(author.getId()).build();
        return updateTopic(topicEntity) && updateTopicPostsCount(id);
    }

    @Override
    public boolean toggleTopicComment(long id, boolean enable) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).commentStatus(Byte.parseByte(Boolean.toString(enable))).build();
        return updateTopic(topicEntity);
    }

    @Override
    public boolean deleteTopic(long id) {

        TopicEntity topicEntity = TopicEntity.builder().id(id).status(Byte.parseByte("0")).build();
        return updateTopic(topicEntity);
    }

    @Override
    public Pagination<Topic> getTopics(int page, int pageSize, String orderClause) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);

            TopicEntityExample topicEntityExample = TopicEntityExample.builder().oredCriteria(new ArrayList<>()).offset((page - 1) * pageSize).limit(pageSize).orderByClause(orderClause).build();
            Byte normalStatus = 1;
            topicEntityExample.or().andStatusEqualTo(normalStatus);
            List<TopicEntity> topicEntities = mapper.selectByExampleWithBLOBs(topicEntityExample);
            List<Topic> topics = assembleTopics(topicEntities);

            long total = 0l;
            if (page == 1) {
                // 仅在第一页请求查询Total
                TopicEntityExample countTopicEntityExample = TopicEntityExample.builder().oredCriteria(new ArrayList<>()).build();
                countTopicEntityExample.or().andStatusIn(new ArrayList<>(Collections.singletonList(normalStatus)));
                total = mapper.countByExample(countTopicEntityExample);
            }

            return new Pagination<>(total, page, pageSize, topics);
        }
    }

    @Override
    public Pagination<Topic> getChannelTopics(int page, int pageSize, int channelId, String orderClause) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);

            TopicEntityExample topicEntityExample = TopicEntityExample.builder().oredCriteria(new ArrayList<>()).offset((page - 1)*pageSize).limit(pageSize).orderByClause(orderClause).build();
            Byte normalStatus = 1;
            topicEntityExample.or().andCidEqualTo(channelId).andStatusIn(new ArrayList<>(Collections.singletonList(normalStatus)));
            List<TopicEntity> topicEntities = mapper.selectByExampleWithBLOBs(topicEntityExample);
            List<Topic> topics = assembleTopics(topicEntities);

            long total = 0l;
            if (page == 1) {
                // 仅在第一页请求查询Total
                TopicEntityExample countTopicEntityExample = TopicEntityExample.builder().oredCriteria(new ArrayList<>()).build();
                countTopicEntityExample.or().andCidEqualTo(channelId).andStatusIn(new ArrayList<>(Collections.singletonList(normalStatus)));
                total = mapper.countByExample(countTopicEntityExample);
            }

            return new Pagination<>(total, page, pageSize, topics);
        }
    }

    private List<Topic> assembleTopics(List<TopicEntity> topicEntities) {

        List<Topic> topics = new ArrayList<>();
        List<Integer> channelIds = topicEntities.stream().map(TopicEntity::getCid).distinct().collect(Collectors.toList());
        Map<Integer, Channel> channelMap = channelService.getChannelsByIdList(channelIds).stream().collect(Collectors.toMap(Channel::getId, Function.identity()));

        List<Long> authorIds = topicEntities.stream().map(TopicEntity::getAuthorId).distinct().collect(Collectors.toList());
        Map<Long, User> userMap = userService.getUsersByIdList(authorIds).stream().collect(Collectors.toMap(User::getId, Function.identity()));

        topicEntities.forEach(topicEntity -> {
            Topic topic = DozerMapperUtil.map(topicEntity, Topic.class);
            topic.setChannel(channelMap.get(topicEntity.getCid()));
            topic.setAuthor(userMap.get(topicEntity.getAuthorId()));
            topic.setTags(tagService.getTopicTags(topicEntity.getId()));

            topics.add(topic);

        });

        return topics;
    }

    private boolean updateTopic(TopicEntity topicEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
            int update = mapper.updateByPrimaryKeySelective(topicEntity);
            sqlSession.commit();

            return update > 0;
        }
    }

    private boolean increaseUpdateTopic(TopicEntity topicEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
            int update = mapper.increaseByPrimaryKeySelective(topicEntity);
            sqlSession.commit();

            return update > 0;
        }
    }

    private boolean decreaseUpdateTopic(TopicEntity topicEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
            int update = mapper.decreaseByPrimaryKeySelective(topicEntity);
            sqlSession.commit();

            return update > 0;
        }
    }
}
