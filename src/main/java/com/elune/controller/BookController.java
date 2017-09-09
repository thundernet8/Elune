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


package com.elune.controller;

import com.elune.dal.DBManager;
import com.elune.dal.mapper.BookMapper;
import com.elune.dal.model.Book;

import com.fedepot.mvc.annotation.FromBody;
import com.fedepot.mvc.annotation.HttpPost;
import com.fedepot.mvc.annotation.Route;
import com.fedepot.mvc.annotation.RoutePrefix;
import com.fedepot.mvc.controller.Controller;
import org.apache.ibatis.session.SqlSession;

@RoutePrefix("books")
public class BookController extends Controller {

    private DBManager dbManager;

    public BookController(DBManager dbManager) {

        this.dbManager = dbManager;
    }

    @Route("{int:id}")
    public String getBookDetail(int id) {

        SqlSession sqlSession = dbManager.getSqlSession();
        try {

            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
            Book book = mapper.selectBook(id);

            String message;

            if (book == null) {

                message = "Book with id " + id + " is not exist";
            } else {

                message = "Book detail name " + book.name;
            }

            return message;
        } finally {

            sqlSession.close();
        }
    }

    @HttpPost
    @Route("")
    public void addBook(@FromBody Book book) {

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
