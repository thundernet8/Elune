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


package com.elune.configuration;

import lombok.extern.slf4j.Slf4j;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

import static com.elune.constants.Constant.*;

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
    public static AppConfiguration fromXml(InputStream is) throws Exception {

        try {

            Properties properties = ConfigurationFactory.parseAppXml(is);
            return new AppConfiguration(properties);
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
                            NodeList props = element.getElementsByTagName("property");
                            for (int j=0; j<props.getLength(); j++) {
                                Element propEle = (Element)props.item(j);
                                String name = propEle.getAttribute("name");
                                switch (name) {

                                    case "host":
                                        properties.put(CONFIG_KEY_MYSQL_HOST, propEle.getTextContent());
                                        break;
                                    case "port":
                                        properties.put(CONFIG_KEY_MYSQL_PORT, Integer.parseInt(propEle.getTextContent()));
                                        break;
                                    case "user":
                                        properties.put(CONFIG_KEY_MYSQL_USER, propEle.getTextContent());
                                        break;
                                    case "pass":
                                        properties.put(CONFIG_KEY_MYSQL_PASS, propEle.getTextContent());
                                        break;
                                    case "database":
                                        properties.put(CONFIG_KEY_MYSQL_DBNAME, propEle.getTextContent());
                                        break;
                                }
                            }
                            break;
                        case "redis":
                            Element element2 = (Element)node;
                            NodeList props2 = element2.getElementsByTagName("property");
                            for (int j=0; j<props2.getLength(); j++) {
                                Element propEle = (Element)props2.item(j);
                                String name = propEle.getAttribute("name");
                                switch (name) {

                                    case "host":
                                        properties.put(CONFIG_KEY_REDIS_HOST, propEle.getTextContent());
                                        break;
                                    case "port":
                                        properties.put(CONFIG_KEY_REDIS_PORT, Integer.parseInt(propEle.getTextContent()));
                                        break;
                                    case "pass":
                                        properties.put(CONFIG_KEY_REDIS_PASS, propEle.getTextContent());
                                        break;
                                }
                            }
                            break;
                    }
                }
            }
        }

        // Origin whitelist
        NodeList originNodes = doc.getElementsByTagName("origins");
        if (originNodes.getLength() > 0) {

            Element ele = (Element)originNodes.item(0);
            NodeList origins = ele.getElementsByTagName("origin");
            List<String> whitelist = new ArrayList<>();

            for (int i=0; i<origins.getLength(); i++) {

                Node node = origins.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    whitelist.add(node.getTextContent());
                }
            }

            properties.put(CONFIG_KEY_ORIGIN_WHITELIST, whitelist.toArray());
        }

        // Resource relative path
        NodeList resPathNodes = doc.getElementsByTagName("resource");
        String resRelativePath = "";
        if (resPathNodes.getLength() > 0) {

            Element ele = (Element)resPathNodes.item(0);
            resRelativePath = ele.getTextContent();
        }
        if (resRelativePath.isEmpty()) {

            resRelativePath = "Resources";
        }
        properties.put(CONFIG_KEY_RESOURCE_RELATIVE_PATH, resRelativePath);

        // Environment
        NodeList envNodes = doc.getElementsByTagName("development");
        if (envNodes.getLength() > 0) {

            Element ele = (Element)envNodes.item(0);
            properties.put(CONFIG_KEY_APP_DEV_MODE, !(ele.getTextContent().equals("false")));
        }

        // Site properties
        NodeList siteNodes = doc.getElementsByTagName("site");
        if (siteNodes.getLength() > 0) {

            Element ele = (Element)siteNodes.item(0);
            NodeList props = ele.getElementsByTagName("property");
            for (int j=0; j<props.getLength(); j++) {
                Element propEle = (Element)props.item(j);
                String name = propEle.getAttribute("name");
                switch (name) {

                    case "home":
                        properties.put(CONFIG_KEY_SITE_HOME, propEle.getTextContent());
                        break;

                    case "frontend":
                        properties.put(CONFIG_KEY_SITE_FRONTEND_HOME, propEle.getTextContent());
                        break;
                }
            }
        }

        // SMTP
        NodeList smtpNodes = doc.getElementsByTagName("smtp");
        if (smtpNodes.getLength() > 0) {

            Element ele = (Element)smtpNodes.item(0);
            NodeList props = ele.getElementsByTagName("property");
            for (int j=0; j<props.getLength(); j++) {
                Element propEle = (Element)props.item(j);
                String name = propEle.getAttribute("name");
                switch (name) {

                    case "host":
                        properties.put(CONFIG_KEY_SMTP_HOST, propEle.getTextContent());
                        break;
                    case "port":
                        properties.put(CONFIG_KEY_SMTP_PORT, propEle.getTextContent());
                        break;
                    case "secure":
                        properties.put(CONFIG_KEY_SMTP_SECURE, propEle.getTextContent());
                        break;
                    case "username":
                        properties.put(CONFIG_KEY_SMTP_USERNAME, propEle.getTextContent());
                        break;
                    case "pass":
                        properties.put(CONFIG_KEY_SMTP_PASS, propEle.getTextContent());
                        break;
                }
            }
        }

        return properties;
    }
}
