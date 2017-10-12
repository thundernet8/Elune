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


package com.elune.constants;

import java.io.File;
import java.time.ZoneId;

public interface Constant {

    /**
     * Root folder, for running a packaged jar file, it will be the folder contain this jar; for source code running, it will be the target folder contains compiled classes folder.
     */
    String ROOT_FOLDER = new File(Constant.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath();

    /**
     * Configuration keys
     */
    String CONFIG_KEY_MYSQL_HOST = "app.db.mysql.host";

    String CONFIG_KEY_MYSQL_PORT = "app.db.mysql.port";

    String CONFIG_KEY_MYSQL_USER = "app.db.mysql.user";

    String CONFIG_KEY_MYSQL_PASS = "app.db.mysql.pass";

    String CONFIG_KEY_MYSQL_DBNAME = "app.db.mysql.name";

    String CONFIG_KEY_REDIS_HOST = "app.db.redis.host";

    String CONFIG_KEY_REDIS_PORT = "app.db.redis.port";

    String CONFIG_KEY_REDIS_PASS = "app.db.redis.pass";

    String CONFIG_KEY_APP_DEV_MODE = "app.env.mode.dev";

    String CONFIG_KEY_ORIGIN_WHITELIST = "app.env.origin.whitelist";

    String CONFIG_KEY_RESOURCE_RELATIVE_PATH = "app.env.resource.path";

    String CONFIG_KEY_CONTENT_ABS_PATH = "app.env.content.abspath";

    String CONFIG_KEY_SITE_HOME = "app.env.site.home";

    String CONFIG_KEY_SMTP_HOST = "app.env.smtp.host";

    String CONFIG_KEY_SMTP_PORT = "app.env.smtp.port";

    String CONFIG_KEY_SMTP_SECURE = "app.env.smtp.secure";

    String CONFIG_KEY_SMTP_USERNAME = "app.env.smtp.username";

    String CONFIG_KEY_SMTP_PASS = "app.env.smtp.pass";

    /**
     * Default values
     */
    String DEFAULT_MYSQL_HOST = "localhost";

    int DEFAULT_MYSQL_PORT = 3306;

    String DEFAULT_MEYSQL_DBNAME = "elune";

    String DEFAULT_REDIS_HOST = "localhost";

    int DEFAULT_REDIS_PORT = 6379;

    /**
     * System
     */
    ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Shanghai");
}
