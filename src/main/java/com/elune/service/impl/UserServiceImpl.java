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


package com.elune.service.impl;

import com.elune.constants.UserStatus;
import com.elune.dal.DBManager;
import com.elune.dao.UserMapper;
import com.elune.entity.UserEntity;
import com.elune.model.*;
import com.elune.service.UserService;
import com.elune.utils.DateUtil;
import com.elune.utils.EncryptUtil;
import com.elune.utils.StringUtil;

import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import org.apache.ibatis.session.SqlSession;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @FromService
    private DBManager dbManager;

    @Override
    public LoginUser signin(LoginModel loginModel) throws Exception {

        String username = loginModel.username;
        String password = loginModel.password;

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            UserEntity userEntity;
            if (StringUtil.isEmail(username)) {

                userEntity = mapper.selectByEmail(username);
            } else {

                userEntity = mapper.selectByUsername(username);
            }

            if (userEntity == null) {

                throw new HttpException("用户名错误", 404);
            }

            String md5Pass = EncryptUtil.md5(password);
            if (!userEntity.getPassword().equals(md5Pass)) {

                throw new HttpException("密码错误", 400);
            }

            if (userEntity.getStatus() == UserStatus.DELETE) {

                throw new HttpException("你的账户已禁用", 403);
            }

            // TODO login hooks
            // TODO update last seen
            // TODO update usermeta for login info

            return LoginUser.builder().id(userEntity.getId()).username(userEntity.getUsername()).nickname(userEntity.getNickname()).email(userEntity.getEmail()).joinTime(userEntity.getJoinTime()).build();
        } catch (Exception e) {

            log.error("User {} Login failed", loginModel.username, e);

            if (!(e instanceof HttpException)) {

                throw new Exception("登录失败");
            } else {

                throw e;
            }
        }
    }

    @Override
    public User signup(RegisterModel registerModel) throws Exception {

        String username = registerModel.username;
        String password = registerModel.password;
        String email = registerModel.email;
        String errMsg = null;

        if (username.length() < 4) {

            errMsg = "用户名长度不得少于4位";
        } else if (password.length() < 6) {

            errMsg = "密码长度不得少于6位";
        } else if (!StringUtil.isEmail(email)) {

            errMsg = "邮箱格式不合法";
        } else if (getUserByName(username) != null) {

            errMsg = "用户名已被注册";
        } else if (getUserByEmail(email) != null) {

            errMsg = "邮箱已被注册";
        }

        if (errMsg == null) {

            try (SqlSession sqlSession = dbManager.getSqlSession()) {

                UserMapper mapper = sqlSession.getMapper(UserMapper.class);

                String md5Pass = EncryptUtil.md5(password);
                int joinTime = DateUtil.getTimeStamp();

                UserEntity userEntity = UserEntity.builder().username(username).nickname(username).password(md5Pass).email(email).joinTime(joinTime).build();
                mapper.insertSelective(userEntity);
                long uid = userEntity.getId();
                sqlSession.commit();

                // TODO send verify email(event queue)

                return User.builder().id(uid).username(username).nickname(username).email(email).joinTime(joinTime).build();
            } catch (Exception e) {

                log.error("Insert user failed", e);
                errMsg = e.getMessage();
            }
        }

        throw new HttpException(errMsg, 400);
    }

    @Override
    public User getUserByName(String username) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(long uid) {
        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean updateInfo(Map<String, Object> info) {
        return false;
    }

    @Override
    public int getUnReadCount(long uid) {
        return 0;
    }

    @Override
    public Pagination<Notification> getNotifications(long uid, int page, int pageSize) {
        return null;
    }

    @Override
    public User getUser(long id) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            UserEntity userEntity = mapper.selectByPrimaryKey(id);
            if (userEntity == null) {

                return null;
            }

            return User.builder().id(userEntity.getId()).username(userEntity.getUsername()).nickname(userEntity.getNickname()).email(userEntity.getEmail()).joinTime(userEntity.getJoinTime()).build();
        }
    }

    @Override
    public UserEntity getUserEntity(long id) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectByPrimaryKey(id);
        }
    }
}
