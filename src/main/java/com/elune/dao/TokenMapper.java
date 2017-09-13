package com.elune.dao;

import com.elune.entity.TokenEntity;
import com.elune.entity.TokenEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TokenMapper {
    long countByExample(TokenEntityExample example);

    int deleteByExample(TokenEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TokenEntity record);

    int insertSelective(TokenEntity record);

    List<TokenEntity> selectByExample(TokenEntityExample example);

    TokenEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TokenEntity record, @Param("example") TokenEntityExample example);

    int updateByExample(@Param("record") TokenEntity record, @Param("example") TokenEntityExample example);

    int updateByPrimaryKeySelective(TokenEntity record);

    int updateByPrimaryKey(TokenEntity record);
}