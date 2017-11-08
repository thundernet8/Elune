package com.elune.dao;

import com.elune.entity.BalancelogEntity;
import com.elune.entity.BalancelogEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BalancelogMapper {
    long countByExample(BalancelogEntityExample example);

    int deleteByExample(BalancelogEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BalancelogEntity record);

    int insertSelective(BalancelogEntity record);

    List<BalancelogEntity> selectByExample(BalancelogEntityExample example);

    BalancelogEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BalancelogEntity record, @Param("example") BalancelogEntityExample example);

    int updateByExample(@Param("record") BalancelogEntity record, @Param("example") BalancelogEntityExample example);

    int updateByPrimaryKeySelective(BalancelogEntity record);

    int updateByPrimaryKey(BalancelogEntity record);
}