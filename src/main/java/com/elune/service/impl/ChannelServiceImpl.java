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


package com.elune.service.impl;

import com.elune.dal.DBManager;
import com.elune.dao.ChannelMapper;
import com.elune.entity.*;
import com.elune.model.*;
import com.elune.service.ChannelService;
import com.elune.utils.DateUtil;
import com.elune.utils.DozerMapperUtil;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ChannelServiceImpl implements ChannelService {

    @FromService
    private DBManager dbManager;

    @Override
    public Channel getChannel(int id) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            ChannelMapper mapper = sqlSession.getMapper(ChannelMapper.class);
            ChannelEntity channelEntity = mapper.selectByPrimaryKey(id);
            if (channelEntity == null) {

                return null;
            }

            Channel channel = DozerMapperUtil.map(channelEntity, Channel.class);
            channel.setLink("/channel/".concat(channel.getSlug()));
            channel.setColor("#".concat(Integer.toHexString(channel.getMainColor())));

            return channel;
        }
    }

    @Override
    public Channel getChannelBySlug(String slug) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            ChannelMapper mapper = sqlSession.getMapper(ChannelMapper.class);

            ChannelEntityExample channelEntityExample = ChannelEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            Byte normalStatus = 1;
            channelEntityExample.or().andSlugEqualTo(slug).andStatusIn(new ArrayList<>(Collections.singletonList(normalStatus)));

            List<Channel> channels = assembleChannel(mapper.selectByExample(channelEntityExample));

            if (channels.size() > 0) {

                return channels.get(0);
            }

            return null;
        }
    }

    @Override
    public int createChannel(ChannelCreationModel channelCreationModel) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            ChannelMapper mapper = sqlSession.getMapper(ChannelMapper.class);

            ChannelEntity channelEntity = ChannelEntity.builder().pid(channelCreationModel.parentId).title(channelCreationModel.title).description(channelCreationModel.description).slug(channelCreationModel.slug).coverImg(channelCreationModel.coverImg).mainColor(Integer.parseInt(channelCreationModel.mainColor.substring(1), 16)).createTime(DateUtil.getTimeStamp()).build();
            mapper.insertSelective(channelEntity);
            sqlSession.commit();

            return channelEntity.getId();
        } catch (Exception e) {

            log.error("Insert channel failed", e);
            throw e;
        }
    }

    @Override
    public boolean updateChannel(ChannelUpdateModel channelUpdateModel) {

        ChannelEntity channelEntity = ChannelEntity.builder().id(channelUpdateModel.id).pid(channelUpdateModel.parentId).title(channelUpdateModel.title).description(channelUpdateModel.description).slug(channelUpdateModel.slug).coverImg(channelUpdateModel.coverImg).mainColor(Integer.parseInt(channelUpdateModel.mainColor.substring(1), 16)).updateTime(DateUtil.getTimeStamp()).build();

        return updateChannel(channelEntity);
    }

    @Override
    public boolean updateTopicCount(int id, int increase) {

        ChannelEntity channelEntity = ChannelEntity.builder().topicsCount(Math.abs(increase)).build();
        return increase < 0 ? decreaseUpdateChannel(channelEntity) : increaseUpdateChannel(channelEntity);
    }

    @Override
    public boolean updateChannelHost(int[] hosts) {

        ChannelEntity channelEntity = ChannelEntity.builder().hosts(Arrays.stream(hosts).mapToObj(Integer::toString).collect(Collectors.joining(","))).build();
        return updateChannel(channelEntity);
    }

    @Override
    public boolean deleteChannel(int id) {

        ChannelEntity channelEntity = ChannelEntity.builder().status(Byte.parseByte("0")).build();
        return updateChannel(channelEntity);
    }

    @Override
    public List<Channel> getAllChannels() {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            ChannelMapper mapper = sqlSession.getMapper(ChannelMapper.class);

            ChannelEntityExample channelEntityExample = ChannelEntityExample.builder().oredCriteria(new ArrayList<>()).offset(0).limit(100).orderByClause("id ASC").build();
            Byte normalStatus = 1;
            channelEntityExample.or().andStatusIn(new ArrayList<>(Collections.singletonList(normalStatus)));

            return assembleChannel(mapper.selectByExample(channelEntityExample));
        }
    }

    @Override
    public List<Channel> getChannelsByIdList(List<Integer> ids) {

        if (ids.size() < 1) {

            return Collections.emptyList();
        }

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            ChannelMapper mapper = sqlSession.getMapper(ChannelMapper.class);

            ChannelEntityExample channelEntityExample = ChannelEntityExample.builder().oredCriteria(new ArrayList<>()).orderByClause("id ASC").build();
            channelEntityExample.or().andIdIn(ids);

            return assembleChannel(mapper.selectByExample(channelEntityExample));
        }
    }

    private List<Channel> assembleChannel(List<ChannelEntity> channelEntities) {

        List<Channel> channels = new ArrayList<>();

        channelEntities.forEach(channelEntity -> {
            Channel channel = DozerMapperUtil.map(channelEntity, Channel.class);
            channel.setLink("/channel/".concat(channel.getSlug()));
            channel.setColor("#".concat(Integer.toHexString(channel.getMainColor())));
            channels.add(channel);

        });

        return channels;
    }

    private boolean updateChannel(ChannelEntity channelEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            ChannelMapper mapper = sqlSession.getMapper(ChannelMapper.class);
            int update = mapper.updateByPrimaryKeySelective(channelEntity);
            sqlSession.commit();

            return update > 0;
        }
    }

    private boolean increaseUpdateChannel(ChannelEntity channelEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            ChannelMapper mapper = sqlSession.getMapper(ChannelMapper.class);
            int update = mapper.increaseByPrimaryKeySelective(channelEntity);
            sqlSession.commit();

            return update > 0;
        }
    }

    private boolean decreaseUpdateChannel(ChannelEntity channelEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            ChannelMapper mapper = sqlSession.getMapper(ChannelMapper.class);
            int update = mapper.decreaseByPrimaryKeySelective(channelEntity);
            sqlSession.commit();

            return update > 0;
        }
    }
}
