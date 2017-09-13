package com.elune.dao;

import com.elune.entity.PostEntity;
import com.elune.entity.PostEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PostMapper {
    long countByExample(PostEntityExample example);

    int deleteByExample(PostEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PostEntity record);

    int insertSelective(PostEntity record);

    List<PostEntity> selectByExampleWithBLOBs(PostEntityExample example);

    List<PostEntity> selectByExample(PostEntityExample example);

    PostEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PostEntity record, @Param("example") PostEntityExample example);

    int updateByExampleWithBLOBs(@Param("record") PostEntity record, @Param("example") PostEntityExample example);

    int updateByExample(@Param("record") PostEntity record, @Param("example") PostEntityExample example);

    int updateByPrimaryKeySelective(PostEntity record);

    int updateByPrimaryKeyWithBLOBs(PostEntity record);

    int updateByPrimaryKey(PostEntity record);
}