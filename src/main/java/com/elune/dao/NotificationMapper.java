package com.elune.dao;

import com.elune.entity.NotificationEntity;
import com.elune.entity.NotificationEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NotificationMapper {
    long countByExample(NotificationEntityExample example);

    int deleteByExample(NotificationEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NotificationEntity record);

    int insertSelective(NotificationEntity record);

    List<NotificationEntity> selectByExampleWithBLOBs(NotificationEntityExample example);

    List<NotificationEntity> selectByExample(NotificationEntityExample example);

    NotificationEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NotificationEntity record, @Param("example") NotificationEntityExample example);

    int updateByExampleWithBLOBs(@Param("record") NotificationEntity record, @Param("example") NotificationEntityExample example);

    int updateByExample(@Param("record") NotificationEntity record, @Param("example") NotificationEntityExample example);

    int updateByPrimaryKeySelective(NotificationEntity record);

    int updateByPrimaryKeyWithBLOBs(NotificationEntity record);

    int updateByPrimaryKey(NotificationEntity record);
}