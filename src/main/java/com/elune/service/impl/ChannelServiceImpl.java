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
import com.elune.utils.DozerMapperUtil;

import com.fedepot.ioc.annotation.FromService;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public int createChannel(ChannelCreationModel channelCreationModel) {
        return 0;
    }

    @Override
    public boolean updateChannel(ChannelUpdateModel channelUpdateModel) {
        return false;
    }

    @Override
    public boolean updateTopicCount(int id, int increase) {
        return false;
    }

    @Override
    public boolean addChannelHost(int[] hosts) {
        return false;
    }

    @Override
    public boolean removeChannelHost(int[] hosts) {
        return false;
    }

    @Override
    public boolean deleteChannel(int id) {
        return false;
    }

    @Override
    public List<Channel> getAllChannels() {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            ChannelMapper mapper = sqlSession.getMapper(ChannelMapper.class);

            ChannelEntityExample channelEntityExample = ChannelEntityExample.builder().oredCriteria(new ArrayList<>()).offset(0).limit(100).orderByClause("id ASC").build();
            Byte normalStatus = 1;
            channelEntityExample.or().andStatusIn(new ArrayList<>(Collections.singletonList(normalStatus)));
            List<ChannelEntity> channelEntities = mapper.selectByExample(channelEntityExample);
            List<Channel> channels = new ArrayList<>();

            channelEntities.forEach(channelEntity -> {
                Channel channel = DozerMapperUtil.map(channelEntity, Channel.class);
                channel.setLink("/channel/".concat(channel.getSlug()));
                channel.setColor("#".concat(Integer.toHexString(channel.getMainColor())));
                channels.add(channel);

            });

            return channels;
        }
    }
}
