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


package com.elune.service;

import com.elune.entity.UserEntity;
import com.elune.model.*;

import java.util.List;
import java.util.Map;

/**
 * @author Touchumind
 */
public interface UserService {

    User signup(RegisterModel registerModel) throws Exception;

    LoginUser signin(LoginModel loginModel) throws Exception;

    User getUser(long id);

    List<User> getUsersByIdList(List<Long> ids);

    UserEntity getUserEntity(long id);

    User getUserByName(String username);

    User getUserByEmail(String email);

    Map<String, Object> getUserInfo(long uid);

    /**
     * 更新用户 (仅更新User表所包含字段)
     *
     * @param user 用户模型
     * @return 更新成功返回true, 否则false
     */
    boolean update(User user);

    /**
     * 更新用户信息 包含扩展信息(UserMeta)
     *
     * @param info 用户信息数据
     * @return 更新成功返回true, 否则false
     */
    boolean updateInfo(Map<String, Object> info);

    /**
     * 获取未读消息数量
     *
     * @param uid 用户ID
     * @return 未读消息数量
     */
    int getUnReadCount(long uid);

    boolean activate(String token);


    /**
     * 分页查询站内提醒
     *
     * @param uid 用户ID
     * @param page 分页
     * @param pageSize 分页大小
     * @return 分页的提醒对象
     */
    Pagination<Notification> getNotifications(long uid, int page, int pageSize);
}
