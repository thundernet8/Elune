package com.elune.dao;

import com.elune.entity.OptionEntity;
import com.elune.entity.OptionEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OptionMapper {
    long countByExample(OptionEntityExample example);

    int deleteByExample(OptionEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OptionEntity record);

    int insertSelective(OptionEntity record);

    List<OptionEntity> selectByExampleWithBLOBs(OptionEntityExample example);

    List<OptionEntity> selectByExample(OptionEntityExample example);

    OptionEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OptionEntity record, @Param("example") OptionEntityExample example);

    int updateByExampleWithBLOBs(@Param("record") OptionEntity record, @Param("example") OptionEntityExample example);

    int updateByExample(@Param("record") OptionEntity record, @Param("example") OptionEntityExample example);

    int updateByPrimaryKeySelective(OptionEntity record);

    int updateByPrimaryKeyWithBLOBs(OptionEntity record);

    int updateByPrimaryKey(OptionEntity record);
}