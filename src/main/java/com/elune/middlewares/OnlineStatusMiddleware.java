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


package com.elune.middlewares;

import com.elune.dal.RedisManager;
import com.elune.utils.DateUtil;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.http.Request;
import com.fedepot.mvc.http.Response;
import com.fedepot.mvc.http.Session;
import com.fedepot.mvc.middleware.Middleware;
import redis.clients.jedis.Jedis;

import java.time.Instant;

public class OnlineStatusMiddleware implements Middleware{

    @FromService
    private RedisManager redisManager;

    @Override
    public void apply(Request request, Response response) {
        Session session = request.session();
        Object uid = session.attribute("uid");
        String sessionId = session.id();

        if (uid == null && Instant.now().getEpochSecond() - session.createAt() < 10) {
            return;
        }

        Jedis jedis = redisManager.getJedis();
        jedis.pfadd("_online_".concat(DateUtil.getDateStr("HHmm").substring(0, 3)), sessionId);
        if (uid != null) {
            String key = "_session_".concat(uid.toString());
            jedis.set(key, Integer.toString(DateUtil.getTimeStamp()));
            jedis.pfadd("_online_logged_".concat(DateUtil.getDateStr("HHmm").substring(0, 3)), sessionId);

        }
        redisManager.retureRes(jedis);
    }
}
