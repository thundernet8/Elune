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

import com.elune.configuration.AppConfiguration;
import com.elune.constants.Constant;
import com.elune.constants.UserStatus;
import com.elune.dal.DBManager;
import com.elune.dao.UserMapper;
import com.elune.entity.UserEntity;
import com.elune.entity.UserEntityExample;
import com.elune.model.*;
import com.elune.service.MailService;
import com.elune.service.UserService;
import com.elune.utils.DateUtil;
import com.elune.utils.EncryptUtil;
import com.elune.utils.StringUtil;

import com.fedepot.cache.Cache;
import com.fedepot.cache.Ehcache;
import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import org.apache.ibatis.session.SqlSession;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @FromService
    private DBManager dbManager;

    @FromService
    private MailService mailService;

    @FromService
    private AppConfiguration appConfiguration;

    private final Cache cache = Ehcache.newInstance("_USER_ACTIVATION_");

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

                User user = User.builder().id(uid).username(username).nickname(username).email(email).joinTime(joinTime).build();

                // 发送验证激活邮件
                sendActivationEmail(user);

                return user;
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

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            UserEntity userEntity = mapper.selectByEmail(email);
            if (userEntity == null) {

                return null;
            }

            return assembleUser(userEntity);
        }
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
    public boolean activate(String token) {

        long uid = (long)cache.get(token).orElse(0l);

        return uid != 0 && updateUser(UserEntity.builder().id(uid).status(Byte.valueOf("1")).build());
    }

    @Override
    public boolean reActivate(String email) {

        User user = getUserByEmail(email);
        if (user == null) {

            return false;
        }

        sendActivationEmail(user);

        return true;
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

            return assembleUser(userEntity);
        }
    }

    @Override
    public UserEntity getUserEntity(long id) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectByPrimaryKey(id);
        }
    }

    @Override
    public List<User> getUsersByIdList(List<Long> ids) {

        if (ids.size() < 1) {

            return Collections.emptyList();
        }

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            UserEntityExample userEntityExample = UserEntityExample.builder().oredCriteria(new ArrayList<>()).orderByClause("id ASC").build();
            userEntityExample.or().andIdIn(ids);

            return assembleUsers(mapper.selectByExample(userEntityExample));
        }
    }

    private boolean updateUser(UserEntity userEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int update = mapper.updateByPrimaryKeySelective(userEntity);
            sqlSession.commit();

            return update > 0;
        }
    }


    private User assembleUser(UserEntity userEntity) {

        // TODO more fields
        return User.builder().id(userEntity.getId()).username(userEntity.getUsername()).nickname(userEntity.getNickname()).email(userEntity.getEmail()).joinTime(userEntity.getJoinTime()).build();
    }

    private List<User> assembleUsers(List<UserEntity> userEntities) {

        List<User> users = new ArrayList<>();

        userEntities.forEach(userEntity -> {

            users.add(assembleUser(userEntity));
        });

        return users;
    }

    private void sendActivationEmail(User user) {

        String cacheKey = StringUtil.genRandString(32);
        cache.add(cacheKey, user.getId(), 600);
        String link = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/activation?token=").concat(cacheKey);
        mailService.sendMail(user.getEmail(), user.getUsername(), "请激活您的账户", "感谢您注册Elune. 请访问下方链接激活您的账户." + link);
    }
}
