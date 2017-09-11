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


package com.elune.dao.service;

import com.elune.dao.DBManager;
import com.elune.dao.entity.Book;
import com.elune.dao.mapper.BookMapper;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import org.apache.ibatis.session.SqlSession;

@Service
public class BookService {

    @FromService
    private DBManager dbManager;

    public Book getBook(int id) {

        SqlSession sqlSession = dbManager.getSqlSession();
        try {

            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            return mapper.selectBook(id);
        } finally {

            sqlSession.close();
        }
    }

    public void createBook(Book book) {

        SqlSession sqlSession = dbManager.getSqlSession();

        try {

            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            mapper.addBook(book);
            sqlSession.commit();

        } finally {

            sqlSession.close();
        }
    }
}
