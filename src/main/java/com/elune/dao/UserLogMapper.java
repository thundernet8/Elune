package com.elune.dao;

import com.elune.entity.UserlogEntity;
import com.elune.entity.UserlogEntityExample;
import com.elune.entity.UserlogEntityWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserLogMapper {
    long countByExample(UserlogEntityExample example);

    int deleteByExample(UserlogEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserlogEntityWithBLOBs record);

    int insertSelective(UserlogEntityWithBLOBs record);

    List<UserlogEntityWithBLOBs> selectByExampleWithBLOBs(UserlogEntityExample example);

    List<UserlogEntity> selectByExample(UserlogEntityExample example);

    UserlogEntityWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserlogEntityWithBLOBs record, @Param("example") UserlogEntityExample example);

    int updateByExampleWithBLOBs(@Param("record") UserlogEntityWithBLOBs record, @Param("example") UserlogEntityExample example);

    int updateByExample(@Param("record") UserlogEntity record, @Param("example") UserlogEntityExample example);

    int updateByPrimaryKeySelective(UserlogEntityWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserlogEntityWithBLOBs record);

    int updateByPrimaryKey(UserlogEntity record);
}