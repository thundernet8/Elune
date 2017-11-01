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
import com.elune.dao.NotificationMapper;
import com.elune.entity.NotificationEntity;
import com.elune.entity.NotificationEntityExample;
import com.elune.model.Notification;
import com.elune.model.Pagination;
import com.elune.service.NotificationService;
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
public class NotificationServiceImpl implements NotificationService {

    @FromService
    private DBManager dbManager;

    @Override
    public long createNotification(String from, String to, String title, String content, Byte type) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            NotificationMapper mapper = sqlSession.getMapper(NotificationMapper.class);
            NotificationEntity entity = NotificationEntity.builder().status(Byte.valueOf("0")).sender(from).receiver(to).title(title).content(content).type(type).createTime(DateUtil.getTimeStamp()).build();

            mapper.insertSelective(entity);
            sqlSession.commit();

            return entity.getId();

        } catch (Exception e) {

            log.error("Insert notification record failed", e);
            throw e;
        }
    }

    @Override
    public boolean markNotificationRead(long id) {

        NotificationEntity entity = NotificationEntity.builder().id(id).status(Byte.valueOf("1")).build();

        return updateNotification(entity);
    }

    @Override
    public Pagination<Notification> getNotifications(String username, int page, int pageSize, String orderClause) {

        return getNotifications(username, page, pageSize, orderClause, null, null, null);
    }

    @Override
    public List<Notification> getNotifications(String username, List<Long> ids) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            NotificationMapper mapper = sqlSession.getMapper(NotificationMapper.class);
            NotificationEntityExample entityExample = NotificationEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            entityExample.or().andIdIn(ids).andReceiverEqualTo(username);

            return assembleNotifications(mapper.selectByExampleWithBLOBs(entityExample));
        }
    }

    @Override
    public Pagination<Notification> getUnReadNotifications(String username, int page, int pageSize, String orderClause) {

        return getNotifications(username, page, pageSize, orderClause, Byte.valueOf("0"), null, null);
    }

    @Override
    public Pagination<Notification> getUserNotifications(String username, int page, int pageSize, String orderClause) {

        return getNotifications(username, page, pageSize, orderClause, null, null, "User");
    }

    @Override
    public Pagination<Notification> getSystemNotifications(String username, int page, int pageSize, String orderClause) {

        return getNotifications(username, page, pageSize, orderClause, null, null, "System");
    }

    @Override
    public boolean updateNotificationsStatus(List<Long> ids, Byte status) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            NotificationMapper mapper = sqlSession.getMapper(NotificationMapper.class);
            NotificationEntity record = NotificationEntity.builder().status(status).build();
            NotificationEntityExample entityExample = NotificationEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            entityExample.or().andIdIn(ids);
            return mapper.updateByExampleSelective(record, entityExample) > 0;
        }
    }

    private boolean updateNotification(NotificationEntity notificationEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            NotificationMapper mapper = sqlSession.getMapper(NotificationMapper.class);
            int update = mapper.updateByPrimaryKeySelective(notificationEntity);

            return update > 0;
        }
    }

    private Pagination<Notification> getNotifications(String username, int page, int pageSize, String orderClause, Byte readStatus, Byte type, String sender) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            NotificationMapper mapper = sqlSession.getMapper(NotificationMapper.class);
            NotificationEntityExample entityExample = NotificationEntityExample.builder().oredCriteria(new ArrayList<>()).offset((page - 1) * pageSize).limit(pageSize).orderByClause(orderClause).build();

            entityExample.or().andReceiverEqualTo(username);

            if (readStatus != null) {
                entityExample.getOredCriteria().get(0).andStatusEqualTo(readStatus);
            }

            if (type != null) {
                entityExample.getOredCriteria().get(0).andTypeEqualTo(type);
            }

            if (sender != null) {
                if (sender.equals("System")) {
                    entityExample.getOredCriteria().get(0).andSenderNotEqualTo(sender);
                } else {
                    entityExample.getOredCriteria().get(0).andSenderEqualTo(sender);
                }
            }

            List<NotificationEntity> notificationEntities = mapper.selectByExampleWithBLOBs(entityExample);
            List<Notification> notifications = assembleNotifications(notificationEntities);

            long total = 0L;
            if (page == 1) {
                // 仅在第一页请求查询Total
                NotificationEntityExample countEntityExample = NotificationEntityExample.builder().oredCriteria(new ArrayList<>()).build();
                countEntityExample.or().andReceiverEqualTo(username);

                if (readStatus != null) {
                    countEntityExample.getOredCriteria().get(0).andStatusEqualTo(readStatus);
                }

                if (type != null) {
                    countEntityExample.getOredCriteria().get(0).andTypeEqualTo(type);
                }
                total = mapper.countByExample(countEntityExample);
            }

            return new Pagination<>(total, page, pageSize, notifications);
        }
    }

    private List<Notification> assembleNotifications(List<NotificationEntity> notificationEntities) {

        List<Notification> notifications = new ArrayList<>(notificationEntities.size());

        notificationEntities.forEach(entity -> {
            notifications.add(DozerMapperUtil.map(entity, Notification.class));
        });

        return notifications;
    }
}
