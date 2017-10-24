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

import com.elune.dal.DBManager;

import com.elune.model.FavoriteCreationModel;
import com.elune.service.UserMetaService;
import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.*;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.Session;

/**
 * @author Touchumind
 */
@RoutePrefix("api/v1/usermetas")
public class UserMetaController extends APIController {

    private DBManager dbManager;

    @FromService
    private UserMetaService userMetaService;

    public UserMetaController(DBManager dbManager) {

        this.dbManager = dbManager;
    }

    @HttpGet
    @Route("favorites")
    public void getFavorites(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize) {

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");

            if (uid < 1) {
                throw new HttpException("尚未登录", 401);
            }

            Succeed(userMetaService.getFavorites(uid, page, pageSize));
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("favorites")
    public void addFavorites(@FromBody FavoriteCreationModel favoriteCreationModel) {

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");

            if (uid < 1) {
                throw new HttpException("尚未登录", 401);
            }

            Succeed(userMetaService.favoriteTopic(uid, favoriteCreationModel.topicId));
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpDelete
    @Route("favorites/{long:id}")
    public void deleteFavorite(long id) {

        try {

            Session session = Request().session();
            long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");

            if (uid < 1) {
                throw new HttpException("尚未登录", 401);
            }

            Succeed(userMetaService.unfavoriteTopic(uid, id));
        } catch (Exception e) {

            Fail(e);
        }
    }
}
