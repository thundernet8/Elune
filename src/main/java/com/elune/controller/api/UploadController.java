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
import com.elune.service.UserLogMQService;
import com.elune.service.UserService;
import com.elune.utils.EncryptUtil;

import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.ForInject;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.*;
import com.fedepot.mvc.controller.APIController;
import com.fedepot.mvc.http.FormFile;
import com.fedepot.mvc.http.Session;
import com.fedepot.util.DateKit;
import org.apache.commons.io.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static com.elune.constants.Constant.*;
import static com.elune.constants.UserLogType.*;

/**
 * @author Touchumind
 */
@Slf4j
@RoutePrefix("api/v1/upload")
public class UploadController extends APIController{

    private String contentAbsPath;

    private String imageBaseUrl;

    @FromService
    private UserService userService;

    @FromService
    private UserLogMQService userLogMQService;

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

        String dateStr = DateKit.getDateString(Date.from(Instant.now()), "yyyy/MM/dd");
        String basePath = contentAbsPath.concat(File.separator).concat("images").concat(File.separator).concat(dateStr);
        String baseUrl = imageBaseUrl.concat(dateStr).concat("/");

        List<String> imageUrls = new ArrayList<>();
        Iterator<Map.Entry<String, FormFile>> iterator = files.entrySet().iterator();
        try {
            while (iterator.hasNext()) {

                Map.Entry<String, FormFile> entry = iterator.next();
                String imageUrl = saveImage(basePath, baseUrl, entry.getValue());
                imageUrls.add(imageUrl);

                // log
                userLogMQService.createUserLog(uid, L_UPLOAD_IMAGE, "", "上传了图片: ".concat(imageUrl), Request().getIp(), Request().getUa());
            }

            Map<String, Object> resp = new HashMap<>(2);
            resp.put("result", imageUrls);
            resp.put("msg", "图片上传成功");
            Succeed(resp);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpPost
    @Route("avatars")
    public void uploadAvatar(@FormFiles Map<String, FormFile> files) {

        Session session = Request().session();
        long uid = session == null || session.attribute("uid") == null ? 0 : session.attribute("uid");
        if (uid < 1) {

            throw new HttpException("尚未登录, 不能上传头像", 401);
        }

        String basePath = contentAbsPath.concat(File.separator).concat("images").concat(File.separator).concat("avatars");
        String baseUrl = imageBaseUrl.concat("avatars/");

        try {
            Map.Entry<String, FormFile> entry = files.entrySet().iterator().next();
            String imageUrl = saveImage(basePath, baseUrl, entry.getValue());
            Map<String, Object> updateInfo = new HashMap<>(2);
            updateInfo.put("id", uid);
            updateInfo.put("avatar", imageUrl);
            userService.updateInfo(updateInfo);

            // log
            userLogMQService.createUserLog(uid, L_UPLOAD_AVATAR, "", "上传了头像: ".concat(imageUrl), Request().getIp(), Request().getUa());

            Map<String, Object> resp = new HashMap<>(2);
            resp.put("result", imageUrl);
            resp.put("msg", "头像上传成功");

            Succeed(resp);
        } catch (Exception e) {

            Fail(e);
        }
    }

    private String saveImage(String basePath, String baseUrl, FormFile formFile) throws Exception {

        String md5 = EncryptUtil.md5(formFile.getData());
        String fileName = formFile.getFileName();
        String ext = "";
        int index = fileName.lastIndexOf('.');

        if (index >= 0 && index != fileName.length() - 1) {

            ext = fileName.substring(index);
        }
        String filePath = basePath.concat(File.separator).concat(md5).concat(ext);
        String fullUrl = baseUrl.concat(md5).concat(ext);

        try {

            FileUtils.writeByteArrayToFile(new File(filePath), formFile.getData());
            return fullUrl;
        } catch (IOException e) {

            log.error("Write image file {} failed", formFile.getFileName(), e);
            throw e;
        }
    }
}
