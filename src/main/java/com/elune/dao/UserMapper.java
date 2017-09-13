package com.elune.dao;

import com.elune.entity.UserEntity;
import com.elune.entity.UserEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    UserEntity selectByPrimaryKey(Long id);
}
