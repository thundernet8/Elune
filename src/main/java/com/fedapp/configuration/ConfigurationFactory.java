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


package com.fedapp.configuration;

import lombok.extern.slf4j.Slf4j;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

import static com.fedapp.Constant.*;

/**
 * Parse app configuration xml to configuration entity
 *
 * @author Touchumind
 * @since 0.0.1
 */
@Slf4j
public class ConfigurationFactory {

    /**
     * Initialize configuration from xml stream
     *
     * @param is configuration file stream
     * @return Configuration instance
     * @throws Exception exception
     */
    public static Configuration fromXml(InputStream is) throws Exception {

        try {

            Properties properties = ConfigurationFactory.parseAppXml(is);
            return new Configuration(properties);
        } catch (Exception e) {

            log.error("Parse configuration xml stream failed", e);
            throw e;
        }
    }

    private static Properties parseAppXml(InputStream is) throws Exception {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);
        doc.getDocumentElement().normalize();

        Properties properties = new Properties();

        // MySql and other databases connection information
        NodeList dbNodes = doc.getElementsByTagName("database");

        if (dbNodes.getLength() > 0) {

            Node dbNode = dbNodes.item(0);
            NodeList childNodes = dbNode.getChildNodes();
            for (int i=0; i<childNodes.getLength(); i++) {

                Node node = childNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    switch (node.getNodeName()) {

                        case "mysql":
                            Element element = (Element)node;
                            properties.put(CONFIG_KEY_MYSQL_HOST, element.getElementsByTagName("host").item(0).getTextContent());
                            properties.put(CONFIG_KEY_MYSQL_PORT, Integer.valueOf(element.getElementsByTagName("port").item(0).getTextContent()));
                            properties.put(CONFIG_KEY_MYSQL_USER, element.getElementsByTagName("user").item(0).getTextContent());
                            properties.put(CONFIG_KEY_MYSQL_PASS, element.getElementsByTagName("pass").item(0).getTextContent());
                            break;
                        case "redis":
                            Element element2 = (Element)node;
                            properties.put(CONFIG_KEY_REDIS_HOST, element2.getElementsByTagName("host").item(0).getTextContent());
                            properties.put(CONFIG_KEY_REDIS_PORT, Integer.valueOf(element2.getElementsByTagName("port").item(0).getTextContent()));
                            properties.put(CONFIG_KEY_REDIS_PASS, element2.getElementsByTagName("pass").item(0).getTextContent());
                    }
                }
            }
        }


        return properties;
    }
}
