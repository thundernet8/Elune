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
import com.elune.dao.TagMapper;
import com.elune.dao.TagRelationMapper;
import com.elune.entity.*;
import com.elune.model.*;
import com.elune.service.TagService;
import com.elune.utils.DateUtil;
import com.elune.utils.DozerMapperUtil;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TagServiceImpl implements TagService{

    @FromService
    private DBManager dbManager;

    @Override
    public Tag getTag(int id) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagMapper mapper = sqlSession.getMapper(TagMapper.class);
            TagEntity tagEntity = mapper.selectByPrimaryKey(id);
            if (tagEntity == null) {

                return null;
            }

            Tag tag = DozerMapperUtil.map(tagEntity, Tag.class);
            tag.setLink("/tag/".concat(tag.getSlug()));

            return tag;
        }
    }

    @Override
    public int createTag(TagCreationModel tagCreationModel) {

        return createTags(Collections.singletonList(tagCreationModel)).get(0);
    }

    @Override
    public List<Integer> createTags(List<TagCreationModel> tagCreationModels) {

        Integer now = DateUtil.getTimeStamp();
        List<TagEntity> tagEntities = new ArrayList<>();
        tagCreationModels.forEach(tagCreationModel -> {

            TagEntity tagEntity = TagEntity.builder().title(tagCreationModel.title).slug(tagCreationModel.slug).createTime(now).build();
            tagEntities.add(tagEntity);
        });

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagMapper mapper = sqlSession.getMapper(TagMapper.class);
            mapper.batchInsertSelective(tagEntities);
            sqlSession.commit();

            return tagEntities.stream().map(TagEntity::getId).collect(Collectors.toList());
        } catch (Exception e) {

            log.error("Batch insert tags failed", e);
            throw e;
        }
    }

    @Override
    public boolean deleteTag(int id) {
        return false;
    }

    @Override
    public boolean updateTopicCount(int id, int increase) {

        TagEntity tagEntity = TagEntity.builder().id(id).topicsCount(Math.abs(increase)).build();
        return increase < 0 ? decreaseUpdateTag(tagEntity) : increaseUpdateTag(tagEntity);
    }

    @Override
    public Pagination<Tag> getTags(int page, int pageSize, String orderClause) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagMapper mapper = sqlSession.getMapper(TagMapper.class);

            TagEntityExample tagEntityExample = TagEntityExample.builder().oredCriteria(new ArrayList<>()).offset((page - 1) * pageSize).limit(pageSize).orderByClause(orderClause).build();

            List<TagEntity> tagEntities = mapper.selectByExample(tagEntityExample);
            List<Tag> tags = assembleTags(tagEntities);

            long total = 0l;
            if (page == 1) {
                // 仅在第一页请求查询Total
                TagEntityExample countTagEntityExample = TagEntityExample.builder().oredCriteria(new ArrayList<>()).build();
                total = mapper.countByExample(countTagEntityExample);
            }

            return new Pagination<>(total, page, pageSize, tags);
        }
    }

    @Override
    public List<Tag> getTagsByIdList(List<Integer> ids) {

        if (ids.size() < 1) {

            return Collections.emptyList();
        }

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            TagEntityExample tagEntityExample = TagEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            tagEntityExample.or().andIdIn(ids);
            return tagMapper.selectByExample(tagEntityExample).stream().map(x -> {
                Tag tag = DozerMapperUtil.map(x, Tag.class);
                tag.setLink("/tag/".concat(tag.getSlug()));

                return tag;
            }).collect(Collectors.toList());
        }
    }

    @Override
    public List<Tag> getTopicTags(long topicId) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagRelationMapper tagRelationMapper = sqlSession.getMapper(TagRelationMapper.class);

            TagRelationEntityExample entityExample = TagRelationEntityExample.builder().oredCriteria(new ArrayList<>()).distinct(true).build();
            entityExample.or().andTopicIdEqualTo(topicId);

            List<Integer> tagIds = tagRelationMapper.selectByExample(entityExample).stream().map(TagRelationEntity::getTagId).distinct().collect(Collectors.toList());

            return getTagsByIdList(tagIds);
        }
    }

    private List<Tag> assembleTags(List<TagEntity> tagEntities) {

        List<Tag> tags = new ArrayList<>();

        tagEntities.forEach(tagEntity -> {
            Tag tag = DozerMapperUtil.map(tagEntity, Tag.class);
            tag.setLink("/tag/".concat(tag.getSlug()));

            tags.add(tag);
        });

        return tags;
    }

    private boolean updateTag(TagEntity tagEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagMapper mapper = sqlSession.getMapper(TagMapper.class);
            int update = mapper.updateByPrimaryKeySelective(tagEntity);
            sqlSession.commit();

            return update > 0;
        }
    }

    private boolean increaseUpdateTag(TagEntity tagEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagMapper mapper = sqlSession.getMapper(TagMapper.class);
            int update = mapper.increaseByPrimaryKeySelective(tagEntity);
            sqlSession.commit();

            return update > 0;
        }
    }

    private boolean decreaseUpdateTag(TagEntity tagEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagMapper mapper = sqlSession.getMapper(TagMapper.class);
            int update = mapper.decreaseByPrimaryKeySelective(tagEntity);
            sqlSession.commit();

            return update > 0;
        }
    }
}
