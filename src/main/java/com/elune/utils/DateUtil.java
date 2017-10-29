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


package com.elune.utils;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import static com.elune.constants.Constant.DEFAULT_ZONE_ID;

public final class DateUtil {

    /**
     * 获取指定时间的Timestamp
     *
     * @return Timestamp(秒)
     */
    public static int getTimeStamp() {

        return getTimeStamp(LocalDateTime.now());
    }

    /**
     * 获取指定时间的Timestamp
     *
     * @param dateTime 指定时间
     * @return Timestamp(秒)
     */
    public static int getTimeStamp(LocalDateTime dateTime) {

        return Math.toIntExact(dateTime.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli() / 1000);
    }

    /**
     * 获取今日0时的时间戳
     *
     * @param timeZone 时区字符串(e.g GMT+8)
     */
    public static int getDayStartTimeStamp(String timeZone) {

        long now = LocalDateTime.now().atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return (int)(calendar.getTimeInMillis() / 1000);
    }

    /**
     * 获取GMT+8的0时时间戳
     */
    public static int getDayStartTimeStamp() {

        return getDayStartTimeStamp("GMT+8");
    }
}
