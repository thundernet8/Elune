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

import com.elune.dal.DBManager;
import com.elune.entity.UserEntity;
import com.elune.model.User;
import com.elune.dao.UserMapper;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import org.apache.ibatis.session.SqlSession;

@Service
public class UserService {

    @FromService
    private DBManager dbManager;

    public User getUser(long id) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            UserEntity userEntity = mapper.selectByPrimaryKey(id);
            return new User(){
                {
                    id = userEntity.getId();
                    nickname = userEntity.getNickname();
                }
            };
        }
    }

    public void createUser(User user) {

        // Example
        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            UserEntity userEntity = new UserEntity();
            userEntity.setId(user.id);
            userEntity.setNickname(user.nickname);

            sqlSession.commit();

        }
    }
}
