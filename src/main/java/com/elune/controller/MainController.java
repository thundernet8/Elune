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

package com.elune.controller;

import com.fedepot.mvc.annotation.Route;
import com.fedepot.mvc.controller.Controller;

import java.util.HashMap;
import java.util.Map;


/**
 * Main controller
 *
 * @author Touchumind
 * @since 0.0.1
 */
public class MainController extends Controller {

    @Route("")
    public void index() {

        String name = "Elune";

        String des = "Configuration factory Ok";

        Map<String, Object> model = new HashMap<>(2);

        model.put("name", name);
        model.put("des", des);

        View("index.htm", model);
    }

    /**
     * 频道列表
     */
    @Route("channels")
    public void channels() {

        View("index.htm");
    }

    /**
     * 频道详情
     */
    @Route("channel/{int:id}")
    public void channel(int id) {

        View("index.htm");
    }


    /**
     * 标签详情
     */
    @Route("tag/{int:id}")
    public void tag(int id) {

        View("index.htm");
    }


    /**
     * 话题详情
     */
    @Route("topic/{string:id}")
    public void topic(String id) {

        View("index.htm");
    }

    /**
     * 博客首页
     */
    @Route("blog")
    public void blog(String id) {

        View("index.htm");
    }

    /**
     * 博客分类页
     */
    @Route("blog/category/{int:id}")
    public void blogCategory(int id) {

        View("index.htm");
    }

    /**
     * 文章详情
     */
    @Route("article/{string:id}")
    public void article(String id) {

        View("index.htm");
    }

    /**
     * 账户激活页面
     */
    @Route("activation")
    public void activation() {

        View("index.htm");
    }

    /**
     * 管理后台
     */
    @Route("admin")
    public void admin() {

        ViewBag("title", "Elune Dashboard");
        View("admin.htm");
    }

    @Route("admin/*")
    public void admins() {

        View("admin.htm");
    }
}
