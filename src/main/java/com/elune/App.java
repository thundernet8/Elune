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

package com.elune;

import com.elune.configuration.AppConfiguration;
import com.elune.init.AppLoader;

import com.fedepot.Razor;
import com.fedepot.event.EventType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;


/**
 * 应用入口
 *
 * @author Touchumind
 * @since 0.0.1
 */
@Slf4j
public class App {

    @Getter
    @Setter
    private AppConfiguration configuration;

    @Getter
    private Razor razor;

    public static void main(String[] args ) {

        App app = new App();
        app.razor = Razor.self();

        try {

            app.start(args);
        } catch (Exception e) {

            log.info(">>>>>>>>>>>>>>>>>>>>>>>>> APP start failed, exit now <<<<<<<<<<<<<<<<<<<<<<<<<");
            System.exit(-1);
        }
    }

    private void start(String[] args) throws Exception {

        AppLoader.init(this, args);

        razor.start(App.class, args);

        razor.getEventEmitter().on(EventType.APP_STARTED, e -> {

            log.info(">>>>>>>>>>>>>>>>>>>>>>>>> APP started <<<<<<<<<<<<<<<<<<<<<<<<<");
        });
    }

}
