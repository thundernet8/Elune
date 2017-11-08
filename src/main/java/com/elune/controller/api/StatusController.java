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


package com.elune.controller.api;

import com.elune.dal.RedisManager;
import com.elune.utils.DateUtil;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.HttpPost;
import com.fedepot.mvc.annotation.Route;
import com.fedepot.mvc.annotation.RoutePrefix;
import com.fedepot.mvc.controller.APIController;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

@RoutePrefix("api/v1/status")
public class StatusController extends APIController {

    @FromService
    private RedisManager redisManager;

    @HttpPost
    @Route("online")
    public void online() {

        try {

            Jedis jedis = redisManager.getJedis();
            long total = jedis.pfcount("_online_".concat(DateUtil.getDateStr("HHmm").substring(0, 3)));
            long logged = jedis.pfcount("_online_logged_".concat(DateUtil.getDateStr("HHmm").substring(0, 3)));
            redisManager.retureRes(jedis);

            Map<String, Long> resp = new HashMap<>(3);
            resp.put("total", total);
            resp.put("logged", logged);
            resp.put("anonymous", total - logged);
            Succeed(resp);

        } catch (Exception e) {

            Fail(e);
        }
    }
}
