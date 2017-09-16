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


package com.elune.init;

import com.elune.App;
import com.elune.constants.Constant;

import com.elune.configuration.ConfigurationFactory;
import com.fedepot.Razor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Application loader
 *
 * @author Touchumind
 * @since 0.0.1
 */
@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppLoader {

    private App app;

    public static void init(App app, String[] args) throws Exception {

        AppLoader appLoader = new AppLoader(app);

        appLoader.prepareConfiguration(args);

        Razor razor = app.getRazor();

        razor.registerInstance(app);
        razor.registerInstance(app.getConfiguration());

        // 不论程序是打包成jar或者未打包方式运行，不会从classpath路径的加载web资源，而是另选其他文件系统路径
        String rootFolder = Constant.ROOT_FOLDER.endsWith("/target") ? Constant.ROOT_FOLDER.substring(0, Constant.ROOT_FOLDER.length() - 7) : Constant.ROOT_FOLDER;
        razor.webRoot(rootFolder.concat(File.separator).concat("WWW/dist"));
        log.info("---------------------------------------------------------------------------------------------------");
        log.info("Use Web Root: {}", rootFolder.concat(File.separator).concat("WWW/dist"));

        appLoader.loadPlugins();
        appLoader.loadThemes();
    }

    /**
     * 解析App配置
     *
     * @param args 程序启动参数
     * @throws Exception 异常
     */
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

        this.app.setConfiguration(ConfigurationFactory.fromXml(new FileInputStream(new File(configFilePath))));
    }

    private void loadPlugins() {

        // TODO
    }

    private void loadThemes() {

        // TODO
    }
}
