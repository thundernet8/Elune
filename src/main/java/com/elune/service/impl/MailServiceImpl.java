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
import com.elune.service.MailService;

import com.elune.task.MailTask;
import com.fedepot.ioc.annotation.ForInject;
import com.fedepot.ioc.annotation.Service;
import com.fedepot.mvc.json.GsonFactory;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(sington = true)
public class MailServiceImpl implements MailService {

    private Producer producer;

    @ForInject
    public MailServiceImpl(MessageQueue messageQueue) {

        String topic = "QUEUETOPIC::SENDMAIL";
        producer = new Producer(messageQueue, topic);
        Consumer consumer = new Consumer(messageQueue, topic);

        consumer.consume(message -> {
            Gson gson = GsonFactory.getGson();

            MailTask mailTask = gson.fromJson(message, MailTask.class);
            executeMailTask(mailTask);
        });

        consumer.up();
    }

    @Override
    public void sendMail(String from, String senderName, String to, String title, String content) {

        log.info("Send mail task produced on thread: T{}", Thread.currentThread().getId());

        MailTask mailTask = MailTask.builder().from(from).senderName(senderName).to(to).title(title).content(content).build();

        producer.produce(GsonFactory.getGson().toJson(mailTask));
    }

    @Override
    public void sendMail(String to, String title, String content) {

        // TODO query default from and senderName
        String from = "";
        String senderName = "";
        sendMail(from, senderName, to, title, content);
    }

    private void executeMailTask(MailTask mailTask) {

        // TODO
        log.info("------------------------execute queue task------------------------");
        log.info("Send mail task consumed on thread: T{}", Thread.currentThread().getId());
        System.out.println(mailTask.getTitle());
    }
}
