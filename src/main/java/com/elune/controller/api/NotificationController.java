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


package com.elune.controller.api;

import com.elune.entity.UserEntity;
import com.elune.service.NotificationMQService;
import com.elune.service.NotificationService;
import com.elune.service.UserService;
import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.*;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.Session;

/**
 * @author Touchumind
 */
@RoutePrefix("api/v1/notifications")
public class NotificationController extends APIController {

    @FromService
    private NotificationService notificationService;

    @FromService
    private UserService userService;

    @FromService
    private NotificationMQService notificationMQService;

    @HttpGet
    @Route("")
    public void getNotifications(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, @QueryParam("order") String order) {

        if (order == null || !(order.toLowerCase().equals("asc"))) {

            order = "DESC";
        }

        String orderBy = "id";

        StringBuilder sb = new StringBuilder();
        sb.append(orderBy);
        sb.append(" ");
        sb.append(order);

        String orderClause = sb.toString();

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
            if (uid < 1) {

                throw new HttpException("请登录", 401);
            }

            UserEntity user = userService.getUserEntity(uid);

            if (user == null) {

                throw new HttpException("请登录", 401);
            }

            if (user.getStatus().equals(Byte.valueOf("0"))) {

                throw new HttpException("你没有权限(账户未激活或已禁用)", 403);
            }

            Succeed(notificationService.getNotifications(user.getUsername(), page, pageSize, orderClause));
        } catch (Exception e) {

            Fail(e);
        }
    }

}
