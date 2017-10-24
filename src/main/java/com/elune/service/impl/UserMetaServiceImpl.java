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
            entityExample.or().andKeyEqualTo(usermetaEntity.getKey()).andUidEqualTo(usermetaEntity.getUid()).andValueEqualTo(usermetaEntity.getValue());
            return mapper.deleteByExample(entityExample) > 0;
        }
    }

    @Override
    public Pagination<Topic> getFavorites(long uid, int page, int pageSize) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntityExample usermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).distinct(true).offset((page - 1) * pageSize).limit(pageSize).orderByClause("ID DESC").build();
            usermetaEntityExample.or().andKeyEqualTo("favorites");
            List<Long> topicIds = mapper.selectByExample(usermetaEntityExample).stream().map(x -> Long.valueOf(x.getValue())).collect(Collectors.toList());
            List<Topic> topics = topicService.getTopicsByIdList(topicIds);

            long total = 0L;
            if (page == 1) {
                // 仅在第一页请求查询Total
                UsermetaEntityExample countUsermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).distinct(true).build();
                countUsermetaEntityExample.or().andKeyEqualTo("favorites");
                total = mapper.countByExample(countUsermetaEntityExample);
            }

            return new Pagination<>(total, page, pageSize, topics);

        }
    }

    @Override
    public boolean favoriteTopic(long userId, long topicId) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            if (countMetas(userId, "favorites", String.valueOf(topicId)) > 0) {

                return true;
            }

            return this.createUsermeta(UsermetaEntity.builder().key("favorites").value(String.valueOf(topicId)).uid(userId).build()) > 0 && topicService.favoriteTopic(topicId);
        }
    }

    @Override
    public boolean unfavoriteTopic(long userId, long topicId) {

        topicService.unfavoriteTopic(topicId);
        return deleteUsermeta(UsermetaEntity.builder().key("favorites").value(String.valueOf(topicId)).uid(userId).build());
    }

    private long countMetas(long userId, String key, String value) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMetaMapper mapper = sqlSession.getMapper(UserMetaMapper.class);
            UsermetaEntityExample usermetaEntityExample = UsermetaEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            usermetaEntityExample.or().andKeyEqualTo(key).andUidEqualTo(userId).andValueEqualTo(value);

            return mapper.countByExample(usermetaEntityExample);
        }
    }
}
