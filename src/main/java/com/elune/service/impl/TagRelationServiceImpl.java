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
import com.elune.dao.TagRelationMapper;
import com.elune.entity.TagRelationEntity;
import com.elune.entity.TagRelationEntityExample;
import com.elune.service.TagRelationService;
import com.elune.utils.DateUtil;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TagRelationServiceImpl implements TagRelationService {

    @FromService
    private DBManager dbManager;

    @Override
    public List<Integer> getTopicTagIds(long topicId) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagRelationMapper mapper = sqlSession.getMapper(TagRelationMapper.class);
            TagRelationEntityExample example = TagRelationEntityExample.builder().distinct(true).oredCriteria(new ArrayList<>()).build();

            return mapper.selectByExample(example).stream().map(TagRelationEntity::getTagId).collect(Collectors.toList());
        }
    }

    @Override
    public long createTagRelation(long topicId, int tagId) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagRelationMapper mapper = sqlSession.getMapper(TagRelationMapper.class);
            TagRelationEntity tagRelationEntity = TagRelationEntity.builder().tagId(tagId).topicId(topicId).createTime(DateUtil.getTimeStamp()).build();
            mapper.insertSelective(tagRelationEntity);

            sqlSession.commit();

            return tagRelationEntity.getId();
        } catch (Exception e) {

            log.error("Insert tag relation record failed", e);
            throw e;
        }
    }

    @Override
    public void createTagRelations(long topicId, List<Integer> tagIds) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagRelationMapper mapper = sqlSession.getMapper(TagRelationMapper.class);
            List<TagRelationEntity> tagRelationEntities = new ArrayList<>(tagIds.size());
            tagIds.forEach(tagId -> {
                tagRelationEntities.add(TagRelationEntity.builder().tagId(tagId).topicId(topicId).createTime(DateUtil.getTimeStamp()).build());
            });
            mapper.batchInsertSelective(tagRelationEntities);

            sqlSession.commit();
        } catch (Exception e) {

            log.error("Batch insert tag relation records failed", e);
            throw e;
        }
    }
}
