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

public interface BalanceLogType {

    Byte B_REGISTER = 1;

    Byte B_REGISTER_REF = 2;

    Byte B_REGISTER_BE_REF = 3;

    Byte B_CREATE_POST = 4;

    Byte B_TOPIC_BE_REPLIED = 5;

    Byte B_TOPIC_BE_FAVORITED = 6;

    Byte B_TOPIC_BE_CANCEL_FAVORITE = 7;

    Byte B_TOPIC_BE_LIKED = 7;

    Byte B_TOPIC_BE_CANCEL_LIKE = 8;

    Byte B_TOPIC_BE_FOLLOWED = 9;

    Byte B_TOPIC_BE_CANCEL_FOLLOW = 10;

    Byte B_DAILY_SIGN = 20;

    // TODO 消费类型log types > 100

    Byte B_CREATE_TOPIC = 101;
}
