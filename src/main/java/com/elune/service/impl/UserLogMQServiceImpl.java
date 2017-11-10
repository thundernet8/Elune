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
import com.elune.service.UserLogMQService;
import com.elune.service.UserLogService;
import com.elune.task.UserLogTask;

import com.fedepot.ioc.annotation.ForInject;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import com.fedepot.mvc.json.GsonFactory;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(sington = true)
public class UserLogMQServiceImpl implements UserLogMQService {

    private Producer producer;

    @FromService
    private UserLogService userLogService;

    @ForInject
    public UserLogMQServiceImpl(MessageQueue messageQueue) {

        String topic = "QUEUETOPIC::USERLOG";
        producer = new Producer(messageQueue, topic);
        Consumer consumer = new Consumer(messageQueue, topic);

        consumer.consume(message -> {
            Gson gson = GsonFactory.getGson();

            UserLogTask task = gson.fromJson(message, UserLogTask.class);
            executeTask(task);
        });

        consumer.up();
    }

    @Override
    public void createUserLog(long uid, String username, byte type, String before, String after, String link, String ip, String ua) {

        UserLogTask task = UserLogTask.builder().uid(uid).username(username).type(type).before(before).after(after).link(link).ip(ip).ua(ua).build();

        producer.produce(GsonFactory.getGson().toJson(task));
    }

    private void executeTask(UserLogTask task) {

        try {

            userLogService.createUserLog(task.getUid(), task.getUsername(), task.getType(), task.getBefore(), task.getAfter(), task.getLink(), task.getIp(), task.getUa());
        } catch (Exception e) {
            log.info("user log: {}", task);
            log.error("create user log error", e);
            e.printStackTrace();
        }
    }
}
