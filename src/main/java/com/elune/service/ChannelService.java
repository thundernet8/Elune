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


package com.elune.service;

import com.elune.model.*;

import java.util.List;

/**
 * @author Touchumind
 */
public interface ChannelService {

    Channel getChannel(int id);

    Channel getChannelBySlug(String slug);

    int createChannel(ChannelCreationModel channelCreationModel);

    boolean updateChannel(ChannelUpdateModel channelUpdateModel);

    boolean deleteChannel(int id);

    /**
     * 更新频道的帖子数量
     *
     * @param id 频道ID
     * @param increase 变动值，为负则减少计数
     * @return 更新成功返回True
     */
    boolean updateTopicCount(int id, int increase);

    boolean updateChannelHost(int[] hosts);

    List<Channel> getAllChannels();

    List<Channel> getChannelsByIdList(List<Integer> ids);
}
