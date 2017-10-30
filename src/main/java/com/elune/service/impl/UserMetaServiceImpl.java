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
import com.elune.entity.UsermetaEntity;
import com.elune.entity.UsermetaEntityExample;
import com.elune.model.Pagination;
import com.elune.model.Topic;
import com.elune.service.TopicService;
import com.elune.service.UserMetaService;

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
            return mapper.deleteByPrimaryKey(id) > 0;
        }
    }

    @Override
    public boolean deleteUsermeta(UsermetaEntity usermetaEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()){

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntityExample entityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            entityExample.or().andMetaKeyEqualTo(usermetaEntity.getMetaKey()).andUidEqualTo(usermetaEntity.getUid()).andMetaValueEqualTo(usermetaEntity.getMetaValue());
            return mapper.deleteByExample(entityExample) > 0;
        }
    }

    @Override
    public List<UsermetaEntity> getUsermetas(long uid, String metaKey) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntityExample usermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).distinct(true).orderByClause("id DESC").build();
            usermetaEntityExample.or().andUidEqualTo(uid).andMetaKeyEqualTo(metaKey);

            return mapper.selectByExample(usermetaEntityExample);
        }
    }

    @Override
    public UsermetaEntity getSingleUsermeta(long uid, String metaKey) {

        List<UsermetaEntity> usermetaEntities = getUsermetas(uid, metaKey);

        return usermetaEntities.size() > 0 ? usermetaEntities.get(0) : null;
    }

    @Override
    public Long countUsermetas(long uid, String metaKey) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntityExample usermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).distinct(true).orderByClause("id DESC").build();
            usermetaEntityExample.or().andMetaKeyEqualTo(metaKey);
            return mapper.countByExample(usermetaEntityExample);

        }
    }

    @Override
    public Pagination<Topic> getFavorites(long uid, int page, int pageSize) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            List<Long> topicIds = getFavoriteIds(uid);
            List<Topic> topics = topicService.getTopicsByIdList(topicIds);

            long total = 0L;
            if (page == 1) {
                // 仅在第一页请求查询Total
                UsermetaEntityExample countUsermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).distinct(true).build();
                countUsermetaEntityExample.or().andMetaKeyEqualTo("favorites");
                total = mapper.countByExample(countUsermetaEntityExample);
            }

            return new Pagination<>(total, page, pageSize, topics);

        }
    }

    @Override
    public List<Long> getFavoriteIds(long uid) {

        List<UsermetaEntity> usermetaEntities = getUsermetas(uid, "favorites");

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
    public boolean hasSignedToday(long uid) {

        UsermetaEntity signRecordMeta = getSingleUsermeta(uid, "dailySign");
        return signRecordMeta != null && Integer.valueOf(signRecordMeta.getMetaValue()) > DateUtil.getDayStartTimeStamp();
    }

    @Override
    public int getBalance(long uid) {

        UsermetaEntity balanceMeta = getSingleUsermeta(uid, "balance");

        return balanceMeta != null ? Integer.valueOf(balanceMeta.getMetaValue()) : 0;
    }

    @Override
    public boolean changeBalance(long uid, int change) {

        int currentBalance = getBalance(uid);
        int newBalance = Math.max(0, currentBalance + change);

        return createOrUpdateUsermeta(uid, "balance", Integer.toString(newBalance));
    }

    private boolean metaExist(long userId, String key, String value) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntityExample usermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            usermetaEntityExample.or().andMetaKeyEqualTo(key).andUidEqualTo(userId).andMetaValueEqualTo(value);

            return mapper.countByExample(usermetaEntityExample) > 0;
        }
    }
}
