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
import com.elune.configuration.ConfigurationFactory;
import com.elune.init.AppLoader;

import com.fedepot.Razor;
import com.fedepot.event.EventType;
import com.fedepot.mvc.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/**
 * 应用入口
 *
 * @author Touchumind
 * @since 0.0.1
 */
@Slf4j
public class App {

    private AppConfiguration configuration;

    public AppConfiguration configuration() {

        return configuration;
    }

    private Razor razor;

    public static void main(String[] args ) {

        App app = new App();
        app.razor = Razor.self();

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

        AppLoader.init(razor, this);

        razor.registerInstance(this);
        razor.registerInstance(configuration);

        String rootFolder = Constant.ROOT_FOLDER.endsWith("/target") ? Constant.ROOT_FOLDER.substring(0, Constant.ROOT_FOLDER.length() - 7) : Constant.ROOT_FOLDER;
        razor.webRoot(rootFolder.concat(File.separator).concat("WWW"));
        log.info("---------------------------------------------------------------------------------------------------");
        log.info("Use Web Root: {}", rootFolder.concat(File.separator).concat("WWW"));

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

            String defaultConfigFilePath = Constant.ROOT_FOLDER.concat("/elune_config.xml");

            log.info("Try to find default configuration file: {}", defaultConfigFilePath);

            File defaultConfigFile = new File(defaultConfigFilePath);

            if (!defaultConfigFile.exists()) {

                InputStream sampleConfigStream = App.class.getResourceAsStream("/WEB-INF/elune_config_sample.xml");

                Path targetFilePath = Paths.get(defaultConfigFilePath);

                System.out.println("Target file path: " + targetFilePath);

                try {

                    Files.copy(sampleConfigStream, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                    log.info("A configuration file elune_config.xml is copied to {}, please accomplish it and restart server", defaultConfigFilePath);
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
