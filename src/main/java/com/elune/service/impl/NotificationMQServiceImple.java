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

import com.elune.mq.Consumer;
import com.elune.mq.MessageQueue;
import com.elune.mq.Producer;
import com.elune.service.NotificationMQService;
import com.elune.service.NotificationService;
import com.elune.task.NotificationTask;

import com.fedepot.ioc.annotation.ForInject;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import com.fedepot.mvc.json.GsonFactory;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(sington = true)
public class NotificationMQServiceImple implements NotificationMQService {

    private Producer producer;

    @FromService
    private NotificationService notificationService;

    @ForInject
    public NotificationMQServiceImple(MessageQueue messageQueue) {

        String topic = "QUEUETOPIC::NOTIFICATION";
        producer = new Producer(messageQueue, topic);
        Consumer consumer = new Consumer(messageQueue, topic);

        consumer.consume(message -> {
            Gson gson = GsonFactory.getGson();

            NotificationTask task = gson.fromJson(message, NotificationTask.class);
            executeNotificationTask(task);
        });

        consumer.up();
    }

    @Override
    public void createNotification(String from, String to, String title, String content, Byte type) {

        NotificationTask task = NotificationTask.builder().from(from).to(to).title(title).content(content).type(type).build();
        producer.produce(GsonFactory.getGson().toJson(task));
    }

    @Override
    public void createNotification(String to, String title, String content, Byte type) {

        createNotification("System", to, title, content, type);
    }

    private void executeNotificationTask(NotificationTask task) {

        log.info("notification: {}", task);

        try {

            notificationService.createNotification(task.getFrom(), task.getTo(), task.getTitle(), task.getContent(), task.getType());
        } catch (Exception e) {
            log.error("create notification error", e);
            e.printStackTrace();
            producer.produce(GsonFactory.getGson().toJson(task));
        }
    }
}
