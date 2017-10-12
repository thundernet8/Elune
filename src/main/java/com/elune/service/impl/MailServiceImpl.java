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

import com.elune.configuration.AppConfiguration;
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
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.simplejavamail.mailer.config.TransportStrategy;

import static com.elune.constants.Constant.*;

@Slf4j
@Service(sington = true)
public class MailServiceImpl implements MailService {

    private Producer producer;

    private AppConfiguration appConfig;

    private Mailer mailer;

    @ForInject
    public MailServiceImpl(MessageQueue messageQueue, AppConfiguration appConfig) {

        this.appConfig = appConfig;
        this.initMailer();

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
    public void sendMail(String from, String senderName, String to, String receiverName, String title, String content) {

        log.info("Send mail task produced on thread: T{}", Thread.currentThread().getId());

        MailTask mailTask = MailTask.builder().from(from).senderName(senderName).to(to).title(title).content(content).build();

        producer.produce(GsonFactory.getGson().toJson(mailTask));
    }

    @Override
    public void sendMail(String to, String receiverName, String title, String content) {

        // TODO query default from and senderName
        String from = appConfig.get(CONFIG_KEY_SMTP_USERNAME, "");
        String senderName = "Elune";
        sendMail(from, senderName, to, receiverName, title, content);
    }

    private void executeMailTask(MailTask mailTask) {

        // TODO
        log.info("------------------------execute queue task------------------------");
        log.info("Send mail task consumed on thread: T{}", Thread.currentThread().getId());

        log.info(mailTask.getTitle());

        try {

            Email email = new EmailBuilder()
                .from(mailTask.getSenderName(), mailTask.getFrom())
                .to(mailTask.getReceiverName(), mailTask.getTo())
                .subject(mailTask.getTitle())
                .text(mailTask.getContent())
                .build();
            mailer.sendMail(email);
        } catch (Exception e) {

            log.error("send mail error", e);
            e.printStackTrace();
        }
    }

    private void initMailer() {

        String host = appConfig.get(CONFIG_KEY_SMTP_HOST, "");
        int port = appConfig.getInt(CONFIG_KEY_SMTP_PORT, 465);
        String secure = appConfig.get(CONFIG_KEY_SMTP_SECURE, "tls");
        String username = appConfig.get(CONFIG_KEY_SMTP_USERNAME, "");
        String password = appConfig.get(CONFIG_KEY_SMTP_PASS, "");

        TransportStrategy transportStrategy = TransportStrategy.SMTP_PLAIN;
        if (secure.toLowerCase().equals("tls")) {

            transportStrategy = TransportStrategy.SMTP_TLS;
        } else if (secure.toLowerCase().equals("ssl")) {

            transportStrategy = TransportStrategy.SMTP_SSL;
        }

        mailer = new Mailer(new ServerConfig(host, port, username, password), transportStrategy);
        mailer.setSessionTimeout(30000);
//        mailer.setDebug(appConfig.getBool(CONFIG_KEY_APP_DEV_MODE, false));
    }
}
