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

public interface NotificationType {

    Byte N_TOPIC_REPLY = 1;

    Byte N_AT = 2;

    Byte N_TOPIC_LIKE = 3;

    Byte N_TOPIC_UNLIKE = 4;

    Byte N_TOPIC_FAVORITE = 5;

    Byte N_TOPIC_UNFAVORITE = 6;

    Byte N_TOPIC_STICKY = 7;

    Byte N_TOPIC_UNSTICKY = 8;

    Byte N_TOPIC_ESSENTIAL = 9;

    Byte N_TOPIC_FOLLOW = 10;

    Byte N_TOPIC_UNFOLLOW = 11;

    Byte N_TOPIC_BE_UPDATED = 12;

    Byte N_USER_FOLLOW = 13;

    Byte N_USER_UNFOLLOW = 14;

    Byte N_USER_BE_UPDATED = 15;
}
