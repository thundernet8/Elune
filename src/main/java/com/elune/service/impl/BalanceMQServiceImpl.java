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

import com.elune.mq.Consumer;
import com.elune.mq.MessageQueue;
import com.elune.mq.Producer;
import com.elune.service.BalanceLogService;
import com.elune.service.BalanceMQService;
import com.elune.service.UserMetaService;
import com.elune.task.UserBalanceTask;

import com.fedepot.ioc.annotation.ForInject;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import com.fedepot.mvc.json.GsonFactory;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(sington = true)
public class BalanceMQServiceImpl implements BalanceMQService {

    private Producer producer;

    @FromService
    private BalanceLogService balanceLogService;

    @ForInject
    public BalanceMQServiceImpl(MessageQueue messageQueue) {

        String topic = "QUEUETOPIC::BALANCE";
        producer = new Producer(messageQueue, topic);
        Consumer consumer = new Consumer(messageQueue, topic);

        consumer.consume(message -> {
            Gson gson = GsonFactory.getGson();

            UserBalanceTask task = gson.fromJson(message, UserBalanceTask.class);
            executeBalanceTask(task);
        });

        consumer.up();
    }

    @Override
    public void increaseBalance(long uid, int num, byte type, String content, String link) {

        UserBalanceTask task = UserBalanceTask.builder().uid(uid).change(num).type(type).content(content).link(link).build();
        producer.produce(GsonFactory.getGson().toJson(task));
    }

    @Override
    public void decreaseBalance(long uid, int num, byte type, String content, String link) {

        increaseBalance(uid, num * -1, type, link, content);
    }

    private void executeBalanceTask(UserBalanceTask balanceTask) {

        log.info("------------------------execute queue task------------------------");
        log.info("Update user balance task consumed on thread: T{}", Thread.currentThread().getId());
        log.info("User: {}, Balance Change: {}", balanceTask.getUid(), balanceTask.getChange());

        try {

            balanceLogService.changeBalance(balanceTask.getUid(), balanceTask.getChange(), balanceTask.getType(), balanceTask.getContent(), balanceTask.getLink());

        } catch (Exception e) {

            log.error("change user balance error", e);
            e.printStackTrace();
        }
    }
}
