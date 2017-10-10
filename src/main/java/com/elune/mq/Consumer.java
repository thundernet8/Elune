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


package com.elune.mq;

import java.util.ArrayList;
import java.util.List;

public class Consumer {

    private MessageQueue messageQueue;

    private String topic;

    private boolean running = false;

    private final List<Callback> callbacks = new ArrayList<>();

    public Consumer(MessageQueue messageQueue, String topic) {

        this.messageQueue = messageQueue;
        this.topic = topic;

        messageQueue.registerConsumer(this);
    }

    public void consume(Callback callback) {

        callbacks.add(callback);
    }

    public void up() {

        running = true;

        new Thread(() -> {

            while (running) {

                String message = messageQueue.read(topic);
                if (message != null) {

                    try {

                        for (Callback callback: callbacks) {

                            callback.onMessage(message);
                        }

                        messageQueue.next(topic);
                    } catch (Exception e) {

                        messageQueue.delay(topic);
                    }

                } else {
                    waitForMessages();
                }
            }
        });
    }

    public void down() {

        this.running = false;
    }

    private void waitForMessages() {

        try {

            Thread.sleep(500);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
