/**
 * Elune - Lightweight Forum Powered by Razor
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


package com.elune.service.impl;

import com.elune.dal.DBManager;
import com.elune.dao.UserlogMapper;
import com.elune.entity.UserlogEntity;
import com.elune.entity.UserlogEntityExample;
import com.elune.model.Pagination;
import com.elune.model.UserLog;
import com.elune.service.UserLogService;
import com.elune.utils.DateUtil;
import com.elune.utils.DozerMapperUtil;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserLogServiceImpl implements UserLogService {

    @FromService
    private DBManager dbManager;

    @Override
    public long createUserLog(long uid, byte type, String before, String after, String ip, String ua) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserlogMapper mapper = sqlSession.getMapper(UserlogMapper.class);
            UserlogEntity entity = UserlogEntity.builder().uid(uid).type(type).ip(ip).ua(ua).before(before).after(after).createTime(DateUtil.getTimeStamp()).build();

            mapper.insertSelective(entity);
            sqlSession.commit();

            return entity.getId();

        } catch (Exception e) {

            log.error("Insert user log record failed", e);
            throw e;
        }
    }

    @Override
    public Pagination<UserLog> getUserLogs(long uid, byte type, int page, int pageSize, String orderClause) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserlogMapper mapper = sqlSession.getMapper(UserlogMapper.class);
            UserlogEntityExample entityExample = UserlogEntityExample.builder().oredCriteria(new ArrayList<>()).offset((page - 1) * pageSize).limit(pageSize).orderByClause(orderClause).build();

            entityExample.or().andUidEqualTo(uid).andTypeEqualTo(type);

            List<UserlogEntity> entities = mapper.selectByExampleWithBLOBs(entityExample);
            List<UserLog> logs = assembleUserLogs(entities);

            long total = 0L;
            if (page == 1) {
                // 仅在第一页请求查询Total
                UserlogEntityExample countEntityExample = UserlogEntityExample.builder().oredCriteria(new ArrayList<>()).build();
                countEntityExample.or().andUidEqualTo(uid).andTypeEqualTo(type);

                total = mapper.countByExample(countEntityExample);
            }

            return new Pagination<>(total, page, pageSize, logs);
        }
    }

    private List<UserLog> assembleUserLogs(List<UserlogEntity> entities) {

        List<UserLog> logs = new ArrayList<>(entities.size());

        entities.forEach(entity -> {
            logs.add(DozerMapperUtil.map(entity, UserLog.class));
        });

        return logs;
    }
}
