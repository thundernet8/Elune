package com.elune.dao;

import com.elune.entity.TopicEntity;
import com.elune.entity.TopicEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TopicMapper {
    long countByExample(TopicEntityExample example);

    int deleteByExample(TopicEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TopicEntity record);

    int insertSelective(TopicEntity record);

    List<TopicEntity> selectByExampleWithBLOBs(TopicEntityExample example);

    List<TopicEntity> selectByExample(TopicEntityExample example);

    TopicEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TopicEntity record, @Param("example") TopicEntityExample example);

    int updateByExampleWithBLOBs(@Param("record") TopicEntity record, @Param("example") TopicEntityExample example);

    int updateByExample(@Param("record") TopicEntity record, @Param("example") TopicEntityExample example);

    int updateByPrimaryKeySelective(TopicEntity record);

    int updateByPrimaryKeyWithBLOBs(TopicEntity record);

    int updateByPrimaryKey(TopicEntity record);

    int increaseByPrimaryKeySelective(TopicEntity record);

    int decreaseByPrimaryKeySelective(TopicEntity record);
}
