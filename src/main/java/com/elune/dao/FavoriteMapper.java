package com.elune.dao;

import com.elune.entity.FavoriteEntity;
import com.elune.entity.FavoriteEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FavoriteMapper {
    long countByExample(FavoriteEntityExample example);

    int deleteByExample(FavoriteEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FavoriteEntity record);

    int insertSelective(FavoriteEntity record);

    List<FavoriteEntity> selectByExample(FavoriteEntityExample example);

    FavoriteEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FavoriteEntity record, @Param("example") FavoriteEntityExample example);

    int updateByExample(@Param("record") FavoriteEntity record, @Param("example") FavoriteEntityExample example);

    int updateByPrimaryKeySelective(FavoriteEntity record);

    int updateByPrimaryKey(FavoriteEntity record);
}