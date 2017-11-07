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

import com.elune.entity.UserEntity;
import com.elune.mq.Consumer;
import com.elune.mq.MessageQueue;
import com.elune.mq.Producer;
import com.elune.service.NotificationMQService;
import com.elune.service.TopicActivityNoticeMQService;
import com.elune.service.UserMetaService;
import com.elune.task.TopicActivityNoticeTask;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import com.fedepot.mvc.json.GsonFactory;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service(sington = true)
public class TopicActivityNoticeMQServiceImpl implements TopicActivityNoticeMQService {

    private Producer producer;

    @FromService
    private UserMetaService userMetaService;

    @FromService
    private NotificationMQService notificationMQService;

    public TopicActivityNoticeMQServiceImpl(MessageQueue messageQueue) {

        String topic = "QUEUETOPIC::TOPICACTIVITYNOTICE";
        producer = new Producer(messageQueue, topic);
        Consumer consumer = new Consumer(messageQueue, topic);

        consumer.consume(message -> {
            Gson gson = GsonFactory.getGson();

            TopicActivityNoticeTask task = gson.fromJson(message, TopicActivityNoticeTask.class);
            executeNotificationTask(task);
        });

        consumer.up();
    }

    @Override
    public void noticeTopicFollowers(long topicId, String from, String title, String content, Byte type) {

        TopicActivityNoticeTask task = TopicActivityNoticeTask.builder().from(from).topicId(topicId).title(title).content(content).type(type).build();
        producer.produce(GsonFactory.getGson().toJson(task));
    }

    @Override
    public void noticeTopicFollowers(long topicId, String title, String content, Byte type) {

        noticeTopicFollowers(topicId, "System", title, content, type);
    }

    private void executeNotificationTask(TopicActivityNoticeTask task) {

        log.info("topic activity notification: {}", task);

        if (task.getContent() == null) {

            return;
        }

        try {

            List<UserEntity> followers = userMetaService.getTopicFollowers(task.getTopicId());
            if (followers.size() == 0) {
                return;
            }
            followers.forEach(follower -> {
                notificationMQService.createNotification(task.getFrom(), follower.getUsername(), task.getTitle(), task.getContent(), task.getType());
            });

        } catch (Exception e) {
            log.error("create topic activity notification error", e);
            e.printStackTrace();
            producer.produce(GsonFactory.getGson().toJson(task));
        }
    }
}
