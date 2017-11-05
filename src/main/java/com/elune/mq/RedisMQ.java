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

import com.elune.dal.RedisManager;

import com.fedepot.ioc.annotation.ForInject;
import com.fedepot.ioc.annotation.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;


@Service(sington = true)
public class RedisMQ implements MessageQueue {

    private RedisManager redisManager;

    private final Map<Producer, Producer> producers = new HashMap<>();

    private final Map<Consumer, Consumer> consumers = new HashMap<>();

    @ForInject
    public RedisMQ(RedisManager redisManager) {

        this.redisManager = redisManager;
    }

    @Override
    public void publish(String topic, String message) {

        Jedis jedis = redisManager.getJedis();
        jedis.lpush(topic, message);
        redisManager.retureRes(jedis);
    }

    @Override
    public String read(String topic) {

        Jedis jedis = redisManager.getJedis();
        String message = jedis.rpop(topic);
        redisManager.retureRes(jedis);

        return message;
    }

    @Override
    public void next(String topic) {

    }

    @Override
    public void delay(String topic, String message) {

        publish(topic, message);
    }

    @Override
    public void dispose() {

        producers.keySet().forEach(Producer::down);
        consumers.keySet().forEach(Consumer::down);
    }

    @Override
    public void registerProducer(Producer producer) {

        producers.put(producer, producer);
    }

    @Override
    public void registerConsumer(Consumer consumer) {

        consumers.put(consumer, consumer);
    }

    @Override
    public void cancelProducer(Producer producer) {

        producers.remove(producer);
        producer.down();
    }

    @Override
    public void cancelConsumer(Consumer consumer) {

        consumers.remove(consumer);
        consumer.down();
    }
}
