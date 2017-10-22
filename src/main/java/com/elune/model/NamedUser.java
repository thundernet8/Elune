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


package com.elune.model;

import lombok.*;

import java.io.Serializable;

/**
 * 访问用户主页请求的用户信息模型
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NamedUser extends User implements Serializable {

    private long mentions;

    private long topicsCount;

    private long postsCount;

    /**
     * 只对当前用户自己返回的收藏话题计数
     */
    private long favoritesCount;

    /**
     * 当前是否在线
     */
    private boolean online;

    /**
     * 最后活跃时间
     */
    private int lastSeen;
}
