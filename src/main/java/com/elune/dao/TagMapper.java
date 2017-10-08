/**
 * Elune - Lightweight Forum Powered by Razor.
 * Copyright (C) 2017, Touchumind<chinash2010@gmail.com>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


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

    int increaseByPrimaryKeySelective(TagEntity record);

    int decreaseByPrimaryKeySelective(TagEntity record);

    List<Integer> batchInsertSelective(List<TagEntity> tagEntities);
}
