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


package com.elune.service.impl;

import com.elune.dal.DBManager;
import com.elune.dao.BalancelogMapper;
import com.elune.entity.BalancelogEntity;
import com.elune.entity.BalancelogEntityExample;
import com.elune.entity.UserEntity;
import com.elune.model.BalanceLog;
import com.elune.model.BalanceRank;
import com.elune.model.Pagination;
import com.elune.service.BalanceLogService;
import com.elune.service.UserService;
import com.elune.utils.DateUtil;
import com.elune.utils.DozerMapperUtil;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BalanceLogServiceImpl implements BalanceLogService {

    @FromService
    private DBManager dbManager;

    @FromService
    private UserService userService;

    @Override
    public int getBalance(long uid) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            BalancelogMapper mapper = sqlSession.getMapper(BalancelogMapper.class);
            BalancelogEntityExample example = BalancelogEntityExample.builder().oredCriteria(new ArrayList<>()).limit(1).offset(0).orderByClause("id DESC").build();
            example.or().andUidEqualTo(uid);

            List<BalancelogEntity> entities = mapper.selectByExample(example);
            if (entities != null && entities.size() > 0) {

                return entities.get(0).getBalance();
            }
            return 0;
        }
    }

    @Override
    public boolean changeBalance(long uid, int change, Byte type, String content, String link) {
        int balance = getBalance(uid);
        if (balance + change < 0) {
            return false;
        }
        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            BalancelogMapper mapper = sqlSession.getMapper(BalancelogMapper.class);
            BalancelogEntity entity = BalancelogEntity.builder().uid(uid).amount(change).type(type).cost(type > 100 ? Math.abs(change) : 0).balance(balance + change).link(link).content(content).createTime(DateUtil.getTimeStamp()).build();

            mapper.insertSelective(entity);
            sqlSession.commit();

            return entity.getId() > 0;
        } catch (Exception e) {

            log.error("Insert balance log record failed", e);
            throw e;
        }
    }

    @Override
    public Pagination<BalanceLog> getBalanceLogs(long uid, int page, int pageSize, String orderClause) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            BalancelogMapper mapper = sqlSession.getMapper(BalancelogMapper.class);
            BalancelogEntityExample example = BalancelogEntityExample.builder().oredCriteria(new ArrayList<>()).limit(pageSize).offset((page - 1) * pageSize).orderByClause(orderClause).build();
            example.or().andUidEqualTo(uid);

            List<BalancelogEntity> entities = mapper.selectByExample(example);
            List<BalanceLog> logs = assembleBalanceLogs(entities);

            BalancelogEntityExample countExample = BalancelogEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            countExample.or().andUidEqualTo(uid);
            long total = mapper.countByExample(countExample);

            return new Pagination<>(total, page, pageSize, logs);
        }
    }

    @Override
    public Pagination<BalanceRank> getBalanceRank(int page, int pageSize, String order) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            BalancelogMapper mapper = sqlSession.getMapper(BalancelogMapper.class);
            BalancelogEntityExample example = BalancelogEntityExample.builder().oredCriteria(new ArrayList<>()).limit(pageSize).offset((page - 1) * pageSize).orderByClause("DESC".equals(order.toUpperCase()) ? "DESC" : "ASC").build();

            List<BalancelogEntity> entities = mapper.selectGroupByExample("balance", example);
            List<BalanceRank> ranks = assembleBalanceRanks(entities, false);


            BalancelogEntityExample countExample = BalancelogEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            long total = mapper.countGroupByExample(countExample);

            return new Pagination<>(total, page, pageSize, ranks);
        }
    }

    @Override
    public Pagination<BalanceRank> getBalanceCostRank(int page, int pageSize, String order) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            BalancelogMapper mapper = sqlSession.getMapper(BalancelogMapper.class);
            BalancelogEntityExample example = BalancelogEntityExample.builder().oredCriteria(new ArrayList<>()).limit(pageSize).offset((page - 1) * pageSize).orderByClause("DESC".equals(order.toUpperCase()) ? "DESC" : "ASC").build();

            List<BalancelogEntity> entities = mapper.selectCostSumByExample(example);
            List<BalanceRank> ranks = assembleBalanceRanks(entities, true);


            BalancelogEntityExample countExample = BalancelogEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            long total = mapper.countGroupByExample(countExample);

            return new Pagination<>(total, page, pageSize, ranks);
        }
    }

    private List<BalanceLog> assembleBalanceLogs(List<BalancelogEntity> entities) {

        List<BalanceLog> logs = new ArrayList<>(entities.size());

        entities.forEach(entity -> {
            logs.add(DozerMapperUtil.map(entity, BalanceLog.class));
        });

        return logs;
    }

    private List<BalanceRank> assembleBalanceRanks(List<BalancelogEntity> entities, boolean isCost) {

        List<Long> uids = entities.stream().map(BalancelogEntity::getUid).distinct().collect(Collectors.toList());
        List<UserEntity> userEntities = userService.getUserEntitiesByIdList(uids);
        Map<Long, UserEntity> usersMap = new HashMap<>(userEntities.size());
        userEntities.forEach(userEntity -> {
            usersMap.put(userEntity.getId(), userEntity);
        });

        List<BalanceRank> ranks = new ArrayList<>(entities.size());
        entities.forEach(entity -> {
            BalanceRank rank = new BalanceRank();
            rank.setUser(usersMap.get(entity.getUid()));
            rank.setAmount(isCost ? entity.getCost() : entity.getBalance());
            ranks.add(rank);
        });

        return ranks;
    }
}
