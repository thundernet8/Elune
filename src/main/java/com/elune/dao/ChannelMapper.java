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


package com.elune.dao;

import com.elune.entity.ChannelEntity;
import com.elune.entity.ChannelEntityExample;

import com.elune.model.Channel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChannelMapper {
    long countByExample(ChannelEntityExample example);

    int deleteByExample(ChannelEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChannelEntity record);

    int insertSelective(ChannelEntity record);

    List<ChannelEntity> selectByExample(ChannelEntityExample example);

    ChannelEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChannelEntity record, @Param("example") ChannelEntityExample example);

    int updateByExample(@Param("record") ChannelEntity record, @Param("example") ChannelEntityExample example);

    int updateByPrimaryKeySelective(ChannelEntity record);

    int updateByPrimaryKey(ChannelEntity record);

    int increaseByPrimaryKeySelective(ChannelEntity record);

    int decreaseByPrimaryKeySelective(ChannelEntity record);
}
