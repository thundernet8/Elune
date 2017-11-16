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

public interface UserLogType {

    Byte L_LOGIN = 1;

    Byte L_LOGOUT = 2;

    Byte L_REGISTER = 3;

    Byte L_ACTIVATE_ACCOUNT = 4;

    Byte L_REACTIVATE_EMAIL = 5;

//    Byte L_BALANCE = 10;

    Byte L_CREATE_TOPIC = 20;

    Byte L_UPDATE_TOPIC = 21;

    Byte L_DELETE_TOPIC = 22;

    Byte L_STICKY_TOPIC = 23;

    Byte L_UNSTICKY_TOPIC = 24;

    Byte L_CREATE_POST = 25;

    Byte L_FAVORITE_TOPIC = 30;

    Byte L_UNFAVORITE_TOPIC = 31;

    Byte L_LIKE_TOPIC = 32;

    Byte L_CANCEL_LIKE_TOPIC = 33;

    Byte L_LIKE_POST = 34;

    Byte L_UPLOAD_IMAGE = 40;

    Byte L_UPLOAD_AVATAR = 41;

    Byte L_UPDATE_PROFILE = 50;

    Byte L_READ_NOTIFICATIONS = 60;

    Byte L_FOLLOW_TOPIC = 70;

    Byte L_UNFOLLOW_TOPIC = 71;

    Byte L_FOLLOW_USER = 80;

    Byte L_UNFOLLOW_USER = 81;
}
