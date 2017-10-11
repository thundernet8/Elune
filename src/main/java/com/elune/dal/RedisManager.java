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


package com.elune.dal;

import com.elune.configuration.AppConfiguration;

import com.fedepot.ioc.annotation.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import static com.elune.constants.Constant.*;

@Service(sington = true)
public final class RedisManager {

    private JedisPool pool;

    private AppConfiguration appConfig;

    public RedisManager(AppConfiguration appConfig) {

        this.appConfig = appConfig;
    }

    private void createJedisPool() {

        JedisPoolConfig config = new JedisPoolConfig();

        // 设置最大连接数
        config.setMaxTotal(100);

        // 设置最大阻塞时间(毫秒)
        config.setMaxWaitMillis(1000);

        // 设置空闲连接数
        config.setMaxIdle(10);
        config.setMinIdle(5);

        // 创建连接池
        pool = new JedisPool(config, appConfig.get(CONFIG_KEY_REDIS_HOST, "127.0.0.1"), appConfig.getInt(CONFIG_KEY_REDIS_PORT, 6379), Protocol.DEFAULT_TIMEOUT, appConfig.get(CONFIG_KEY_REDIS_PASS, ""));
    }

    private void poolInit() {

        if (pool == null) {

            synchronized (RedisManager.class) {

                if (pool == null) {

                    createJedisPool();
                }
            }
        }
    }

    public Jedis getJedis() {

        if (pool == null) {

            poolInit();
        }
        return pool.getResource();
    }

    public void retureRes(Jedis jedis) {

        jedis.close();
    }
}
