package com.elune.dao;

import com.elune.entity.TagEntity;
import com.elune.entity.TagEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TagMapper {
    long countByExample(TagEntityExample example);

    int deleteByExample(TagEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TagEntity record);

    int insertSelective(TagEntity record);

    List<TagEntity> selectByExample(TagEntityExample example);

    TagEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TagEntity record, @Param("example") TagEntityExample example);

    int updateByExample(@Param("record") TagEntity record, @Param("example") TagEntityExample example);

    int updateByPrimaryKeySelective(TagEntity record);

    int updateByPrimaryKey(TagEntity record);
}