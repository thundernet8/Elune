package com.elune.dao;

import com.elune.entity.UserlogEntity;
import com.elune.entity.UserlogEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserlogMapper {
    long countByExample(UserlogEntityExample example);

    int deleteByExample(UserlogEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserlogEntity record);

    int insertSelective(UserlogEntity record);

    List<UserlogEntity> selectByExample(UserlogEntityExample example);

    UserlogEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserlogEntity record, @Param("example") UserlogEntityExample example);

    int updateByExample(@Param("record") UserlogEntity record, @Param("example") UserlogEntityExample example);

    int updateByPrimaryKeySelective(UserlogEntity record);

    int updateByPrimaryKey(UserlogEntity record);
}