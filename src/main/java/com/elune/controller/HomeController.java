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
 * Home controller
 *
 * @author Touchumind
 * @since 0.0.1
 */
public class HomeController extends Controller {

    @Route("*")
    public void index() {

        String name = "Elune";

        String des = "Configuration factory Ok";

        Map<String, Object> model = new HashMap<>();

        model.put("name", name);
        model.put("des", des);

        View("index.htm", model);
    }
}
