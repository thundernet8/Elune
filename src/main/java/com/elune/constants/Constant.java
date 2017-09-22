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
    public static String ROOT_FOLDER = new File(Constant.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath();

    /**
     * Configuration keys
     */
    public static String CONFIG_KEY_MYSQL_HOST = "app.db.mysql.host";

    public static String CONFIG_KEY_MYSQL_PORT = "app.db.mysql.port";

    public static String CONFIG_KEY_MYSQL_USER = "app.db.mysql.user";

    public static String CONFIG_KEY_MYSQL_PASS = "app.db.mysql.pass";

    public static String CONFIG_KEY_MYSQL_DBNAME = "app.db.mysql.name";

    public static String CONFIG_KEY_REDIS_HOST = "app.db.redis.host";

    public static String CONFIG_KEY_REDIS_PORT = "app.db.redis.port";

    public static String CONFIG_KEY_REDIS_PASS = "app.db.redis.pass";

    public static String CONFIG_KEY_APP_DEV_MODE = "app.env.mode.dev";

    public static String CONFIG_KEY_ORIGIN_WHITELIST = "app.env.origin.whitelist";

    /**
     * Default values
     */
    public static String DEFAULT_MYSQL_HOST = "localhost";

    public static int DEFAULT_MYSQL_PORT = 3306;

    public static String DEFAULT_MEYSQL_DBNAME = "elune";

    public static String DEFAULT_REDIS_HOST = "localhost";

    public static int DEFAULT_REDIS_PORT = 6379;

    /**
     * System
     */
    public static ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Shanghai");
}
