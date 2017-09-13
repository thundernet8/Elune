package com.elune.dao;

import com.elune.entity.UsermetaEntity;
import com.elune.entity.UsermetaEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMetaMapper {
    long countByExample(UsermetaEntityExample example);

    int deleteByExample(UsermetaEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UsermetaEntity record);

    int insertSelective(UsermetaEntity record);

    List<UsermetaEntity> selectByExampleWithBLOBs(UsermetaEntityExample example);

    List<UsermetaEntity> selectByExample(UsermetaEntityExample example);

    UsermetaEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UsermetaEntity record, @Param("example") UsermetaEntityExample example);

    int updateByExampleWithBLOBs(@Param("record") UsermetaEntity record, @Param("example") UsermetaEntityExample example);

    int updateByExample(@Param("record") UsermetaEntity record, @Param("example") UsermetaEntityExample example);

    int updateByPrimaryKeySelective(UsermetaEntity record);

    int updateByPrimaryKeyWithBLOBs(UsermetaEntity record);

    int updateByPrimaryKey(UsermetaEntity record);
}