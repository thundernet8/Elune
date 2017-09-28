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

import com.elune.configuration.AppConfiguration;
import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.ForInject;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.*;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.FormFile;
import com.fedepot.mvc.http.Session;

import java.util.Map;

import static com.elune.constants.Constant.*;

@RoutePrefix("api/v1/upload")
public class UploadController extends APIController{

    private String contentAbsPath;

    private String imageBaseUrl;

    @ForInject
    public UploadController(AppConfiguration appConfiguration) {

        contentAbsPath = appConfiguration.get(CONFIG_KEY_CONTENT_ABS_PATH, "");
        imageBaseUrl = appConfiguration.get(CONFIG_KEY_SITE_HOME, "http://localhost").concat("/content/images/");
    }

    @HttpPost
    @Route("images")
    public void uploadImage(@FormFiles Map<String, FormFile> files) {

        Session session = Request().session();
        long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
        if (uid < 1) {

            throw new HttpException("尚未登录, 不能上传图片", 401);
        }
    }
}
