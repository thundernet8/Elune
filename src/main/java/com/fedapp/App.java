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

import com.fedapp.configuration.Configuration;
import com.fedapp.configuration.ConfigurationFactory;
import com.fedapp.init.AppLoader;
import com.fedepot.Razor;
import com.fedepot.event.EventType;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/**
 * Hello world!
 *
 */
@Slf4j
public class App {

    private Configuration configuration;

    public Configuration configuration() {

        return configuration;
    }

    public static void main(String[] args ) throws Exception {

        App app = new App();

        try {

            app.prepareConfiguration(args);
            app.startServer(args);
        } catch (Exception e) {

            log.info("App start failed, exit now");
            System.exit(-1);
            //throw e;
        }
    }

    private void startServer(String[] args) {

        Razor razor = Razor.self();

        AppLoader.init(razor, this);

        razor.registerInstance(this);

        razor.webRoot("WWW");
        razor.addStatic("/static");
        razor.listen("0.0.0.0", 9000);

        razor.start(App.class, args);

        razor.getEventEmitter().on(EventType.APP_STARTED, e -> {
            System.out.println("APP started");
        });
    }


    private void prepareConfiguration(String[] args) throws Exception {

        String configFilePath = "";

        for (int i = 0; i < args.length; i++) {

            if (args[i].startsWith("--config") && i < args.length - 1) {

                configFilePath = args[i+1];
                break;
            }
        }

        if (configFilePath.equals("")) {

            log.info("Configuration file is not specified");

            File defaultConfigFile = new File("./elune_config.xml");

            if (!defaultConfigFile.exists()) {

                InputStream sampleConfigStream = App.class.getResourceAsStream("/WEB-INF/elune_config_sample.xml");

                Path targetFilePath = Paths.get("elune_config.xml");

                try {

                    Files.copy(sampleConfigStream, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                    log.info("A configuration file elune_config.xml is copied to the root folder of project, please accomplish it and restart server");
                } catch (Exception e) {

                    log.error("Copy sample configuration file failed", e);
                    throw e;
                } finally {

                    IOUtils.closeQuietly(sampleConfigStream);
                }
            } else {

                log.info("Using configuration file: {}", defaultConfigFile.getAbsolutePath());
            }

            configFilePath = defaultConfigFile.getAbsolutePath();
        }

        this.configuration = ConfigurationFactory.fromXml(new FileInputStream(new File(configFilePath)));
    }
}
