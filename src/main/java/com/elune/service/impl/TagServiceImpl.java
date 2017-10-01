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
import com.elune.utils.DozerMapperUtil;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return 0;
    }

    @Override
    public List<Integer> createTags(List<TagCreationModel> tagCreationModels) {
        return null;
    }

    @Override
    public boolean deleteTag(int id) {
        return false;
    }

    @Override
    public boolean updateTopicCount(int id, int increase) {
        return false;
    }

    @Override
    public Pagination<Tag> getTags(int page, int pageSize, String orderClause) {
        return null;
    }

    @Override
    public List<Tag> getTagsByIdList(List<Integer> ids) {

        if (ids.size() < 1) {

            return Collections.emptyList();
        }

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            TagEntityExample tagEntityExample = TagEntityExample.builder().build();
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

            List<Integer> tagIds = tagRelationMapper.selectByExample(entityExample).stream().map(TagRelationEntity::getTagId).distinct().collect(Collectors.toList());

            return getTagsByIdList(tagIds);
        }
    }
}
