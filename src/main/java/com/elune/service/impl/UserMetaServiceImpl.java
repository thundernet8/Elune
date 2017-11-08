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
import com.elune.dao.UserMetaMapper;
import com.elune.entity.UserEntity;
import com.elune.entity.UsermetaEntity;
import com.elune.entity.UsermetaEntityExample;
import com.elune.model.Pagination;
import com.elune.model.Topic;
import com.elune.model.User;
import com.elune.service.TopicService;
import com.elune.service.UserMetaService;

import com.elune.service.UserService;
import com.elune.utils.DateUtil;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Touchumind
 */
@Service
@Slf4j
public class UserMetaServiceImpl implements UserMetaService {

    @FromService
    private DBManager dbManager;

    @FromService
    private TopicService topicService;

    @FromService
    private UserService userService;

    @Override
    public long createUsermeta(UsermetaEntity usermetaEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            mapper.insertSelective(usermetaEntity);
            sqlSession.commit();

            return usermetaEntity.getId();

        } catch (Exception e) {

            log.error("Insert user meta failed", e);
            throw e;
        }
    }

    @Override
    public boolean createOrUpdateUsermeta(long uid, String metaKey, String metaValue) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntity usermetaEntity = UsermetaEntity.builder().uid(uid).metaKey(metaKey).metaValue(metaValue).build();

            if (countUsermetas(uid, metaKey) > 0) {
                UsermetaEntityExample usermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).build();
                usermetaEntityExample.or().andUidEqualTo(uid).andMetaKeyEqualTo(metaKey);
                mapper.updateByExampleSelective(usermetaEntity, usermetaEntityExample);
                sqlSession.commit();
            } else {
                mapper.insertSelective(usermetaEntity);
            }
            sqlSession.commit();

            return true;

        } catch (Exception e) {

            log.error("Insert or update user meta failed", e);
            throw e;
        }
    }

    @Override
    public boolean deleteUsermeta(long id) {

        try (SqlSession sqlSession = dbManager.getSqlSession()){

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            int result = mapper.deleteByPrimaryKey(id);

            sqlSession.commit();

            return result > 0;
        }
    }

    @Override
    public boolean deleteUsermeta(UsermetaEntity usermetaEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()){

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntityExample entityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            entityExample.or().andMetaKeyEqualTo(usermetaEntity.getMetaKey()).andUidEqualTo(usermetaEntity.getUid()).andMetaValueEqualTo(usermetaEntity.getMetaValue());
            int result = mapper.deleteByExample(entityExample);

            sqlSession.commit();

            return result > 0;
        }
    }

    @Override
    public List<UsermetaEntity> getUsermetas(long uid, String metaKey, int page, int pageSize) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntityExample usermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).distinct(true).orderByClause("id DESC").build();
            if (page > 0 && pageSize > 0) {
                usermetaEntityExample.setLimit(pageSize);
                usermetaEntityExample.setOffset((page - 1) * pageSize);
            }
            usermetaEntityExample.or().andUidEqualTo(uid).andMetaKeyEqualTo(metaKey);

            return mapper.selectByExample(usermetaEntityExample);
        }
    }

    @Override
    public UsermetaEntity getSingleUsermeta(long uid, String metaKey) {

        List<UsermetaEntity> usermetaEntities = getUsermetas(uid, metaKey, 1, 1);

        return usermetaEntities.size() > 0 ? usermetaEntities.get(0) : null;
    }

    @Override
    public Long countUsermetas(long uid, String metaKey) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntityExample usermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).distinct(true).orderByClause("id DESC").build();
            usermetaEntityExample.or().andUidEqualTo(uid).andMetaKeyEqualTo(metaKey);
            return mapper.countByExample(usermetaEntityExample);

        }
    }

    @Override
    public Pagination<Topic> getFavorites(long uid, int page, int pageSize) {

        List<Long> topicIds = getFavoriteIds(uid, page, pageSize);
        List<Topic> topics = topicService.getTopicsByIdList(topicIds);

        long total = countUsermetas(uid, "favorites");

        return new Pagination<>(total, page, pageSize, topics);
    }

    @Override
    public List<Long> getFavoriteIds(long uid) {

        return getFavoriteIds(uid, 0, 0);
    }

    @Override
    public List<Long> getFavoriteIds(long uid, int page, int pageSize) {

        List<UsermetaEntity> usermetaEntities = getUsermetas(uid, "favorites", page, pageSize);

        return usermetaEntities.stream().map(x -> Long.valueOf(x.getMetaValue())).collect(Collectors.toList());
    }

    @Override
    public Long countFavorites(long uid) {

        return countUsermetas(uid, "favorites");
    }

    @Override
    public boolean favoriteTopic(long uid, long topicId) {

        return metaExist(uid, "favorites", String.valueOf(topicId)) || this.createUsermeta(UsermetaEntity.builder().metaKey("favorites").metaValue(String.valueOf(topicId)).uid(uid).build()) > 0;
    }

    @Override
    public boolean unfavoriteTopic(long uid, long topicId) {

        return deleteUsermeta(UsermetaEntity.builder().metaKey("favorites").metaValue(String.valueOf(topicId)).uid(uid).build());
    }

    @Override
    public Pagination<Topic> getFollowingTopics(long uid, int page, int pageSize) {

        List<Long> topicIds = getUsermetas(uid, "following_topics", page, pageSize).stream().map(x -> Long.valueOf(x.getMetaValue())).collect(Collectors.toList());
        List<Topic> topics = topicService.getTopicsByIdList(topicIds);

        long total = countUsermetas(uid, "following_topics");

        return new Pagination<>(total, page, pageSize, topics);
    }

    @Override
    public Long countFollowingTopics(long uid) {

        return countUsermetas(uid, "following_topics");
    }

    @Override
    public List<Long> getFollowingTopicIds(long uid) {

        List<UsermetaEntity> usermetaEntities = getUsermetas(uid, "following_topics", 0, 0);

        return usermetaEntities.stream().map(x -> Long.valueOf(x.getMetaValue())).collect(Collectors.toList());
    }

    @Override
    public boolean followTopic(long uid, long topicId) {

        return metaExist(uid, "following_topics", String.valueOf(topicId)) || this.createUsermeta(UsermetaEntity.builder().metaKey("following_topics").metaValue(String.valueOf(topicId)).uid(uid).build()) > 0;
    }

    @Override
    public boolean unfollowTopic(long uid, long topicId) {

        return deleteUsermeta(UsermetaEntity.builder().metaKey("following_topics").metaValue(String.valueOf(topicId)).uid(uid).build());
    }

    @Override
    public Pagination<User> getFollowingUsers(long uid, int page, int pageSize) {

        List<Long> userIds = getUsermetas(uid, "following_users", page, pageSize).stream().map(x -> Long.valueOf(x.getMetaValue())).collect(Collectors.toList());
        List<User> topics = userService.getUsersByIdList(userIds);

        long total = countUsermetas(uid, "following_users");

        return new Pagination<>(total, page, pageSize, topics);
    }

    @Override
    public List<Long> getFollowingUids(long uid) {

        List<Long> userIds = getUsermetas(uid, "following_users", 0, 0).stream().map(x -> Long.valueOf(x.getMetaValue())).collect(Collectors.toList());
        return userIds;
    }

    @Override
    public Long countFollowingUsers(long uid) {

        return countUsermetas(uid, "following_users");
    }

    @Override
    public boolean followUser(long uid, long followedUid) {

        return metaExist(uid, "following_users", String.valueOf(followedUid)) || this.createUsermeta(UsermetaEntity.builder().metaKey("following_users").metaValue(String.valueOf(followedUid)).uid(uid).build()) > 0;
    }

    @Override
    public boolean unfollowUser(long uid, long unfollowedUid) {

        return deleteUsermeta(UsermetaEntity.builder().metaKey("following_users").metaValue(String.valueOf(unfollowedUid)).uid(uid).build());
    }

    @Override
    public boolean hasSignedToday(long uid) {

        UsermetaEntity signRecordMeta = getSingleUsermeta(uid, "dailySign");
        return signRecordMeta != null && Integer.valueOf(signRecordMeta.getMetaValue()) > DateUtil.getDayStartTimeStamp();
    }

    @Override
    public List<UserEntity> getTopicFollowers(long topicId) {

        List<Long> uids = getMetaOwners("following_topics", Long.toString(topicId));
        return userService.getUserEntitiesByIdList(uids);
    }

    private boolean metaExist(long userId, String key, String value) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntityExample usermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            usermetaEntityExample.or().andMetaKeyEqualTo(key).andUidEqualTo(userId).andMetaValueEqualTo(value);

            return mapper.countByExample(usermetaEntityExample) > 0;
        }
    }

    private List<Long> getMetaOwners(String key, String value) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntityExample usermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).distinct(true).orderByClause("id DESC").build();
            usermetaEntityExample.or().andMetaKeyEqualTo(key).andMetaValueEqualTo(value);

            return mapper.selectByExample(usermetaEntityExample).stream().map(UsermetaEntity::getUid).collect(Collectors.toList());
        }
    }
}
