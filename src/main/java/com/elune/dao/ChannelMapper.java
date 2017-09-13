package com.elune.dao;

import com.elune.entity.ChannelEntity;
import com.elune.entity.ChannelEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
}