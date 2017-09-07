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

package com.fedapp;

import com.fedepot.Razor;
import com.fedepot.event.EventType;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Hello world!
 *
 */
@Slf4j
public class App {
    public static void main( String[] args ) {

        Razor razor = Razor.self();

        razor.webRoot("WWW");
        razor.listen("0.0.0.0", 9000);
        razor.start(App.class, args);

        razor.getEventEmitter().on(EventType.APP_STARTED, e -> {
            System.out.println("APP started");
        });
    }

    private void prepareConfiguration(String[] args) {

        String configFilePath = "";

        for (int i = 0; i < args.length; i++) {

            if (args[i].startsWith("--config") && i < args.length - 1) {

                configFilePath = args[i+1];
                break;
            }
        }

        if (configFilePath.equals("")) {

            log.info("Configuration file is not specified");

//            try {
//
//                FileWriter writer = new FileWriter(new FileOutputStream("./elune_config.xml"));
//            } catch (IOException e) {
//
//                log.error(e.getMessage());
//            }
        }
    }
}
