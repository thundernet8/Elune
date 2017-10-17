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


package com.elune.dal;

import com.elune.configuration.AppConfiguration;

import com.fedepot.ioc.annotation.Service;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

import static com.elune.constants.Constant.*;

@Service(sington = true)
public final class DBManager {

    private AppConfiguration appConfig;

    private Configuration configuration;

    private SqlSessionFactory sqlSessionFactory;

    public DBManager(AppConfiguration appConfig) {

        this.appConfig = appConfig;

        boolean isDev = appConfig.getBool(CONFIG_KEY_APP_DEV_MODE, true);

        String dbHost = appConfig.get(CONFIG_KEY_MYSQL_HOST, DEFAULT_MYSQL_HOST);
        int dbPort = appConfig.getInt(CONFIG_KEY_MYSQL_PORT, DEFAULT_MYSQL_PORT);
        String dbName = appConfig.get(CONFIG_KEY_MYSQL_DBNAME, DEFAULT_MEYSQL_DBNAME);
        String dbUser = appConfig.get(CONFIG_KEY_MYSQL_USER, "");
        String dbPass = appConfig.get(CONFIG_KEY_MYSQL_PASS, "");
        String dbDriver = "com.mysql.cj.jdbc.Driver";
        String connString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf8mb4";

        DataSource dataSource = new PooledDataSource(dbDriver, connString, dbUser, dbPass);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment(isDev ? "development" : "production", transactionFactory, dataSource);
        configuration = new Configuration(environment);

        configuration.addMappers("com.elune.dao");

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }


    public SqlSession getSqlSession() {

        return sqlSessionFactory.openSession();
    }

//    public <T> T getMapper(Class<T> clazz) {
//
//        return getSqlSession().getMapper(clazz);
//    }
}
