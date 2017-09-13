package com.elune.dao;

import com.elune.entity.OpenEntity;
import com.elune.entity.OpenEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpenMapper {
    long countByExample(OpenEntityExample example);

    int deleteByExample(OpenEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpenEntity record);

    int insertSelective(OpenEntity record);

    List<OpenEntity> selectByExample(OpenEntityExample example);

    OpenEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpenEntity record, @Param("example") OpenEntityExample example);

    int updateByExample(@Param("record") OpenEntity record, @Param("example") OpenEntityExample example);

    int updateByPrimaryKeySelective(OpenEntity record);

    int updateByPrimaryKey(OpenEntity record);
}