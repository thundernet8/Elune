package com.elune.dao;

import com.elune.entity.TagRelationEntity;
import com.elune.entity.TagRelationEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TagRelationMapper {
    long countByExample(TagRelationEntityExample example);

    int deleteByExample(TagRelationEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TagRelationEntity record);

    int insertSelective(TagRelationEntity record);

    List<TagRelationEntity> selectByExample(TagRelationEntityExample example);

    TagRelationEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TagRelationEntity record, @Param("example") TagRelationEntityExample example);

    int updateByExample(@Param("record") TagRelationEntity record, @Param("example") TagRelationEntityExample example);

    int updateByPrimaryKeySelective(TagRelationEntity record);

    int updateByPrimaryKey(TagRelationEntity record);

    void batchInsertSelective(List<TagRelationEntity> tagRelationEntities);
}
