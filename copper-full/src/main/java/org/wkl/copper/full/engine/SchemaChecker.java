/*
 * Copyright 2020 Winfried Klum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wkl.copper.full.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Initializes copper postgres schema
 */
public class SchemaChecker implements ServletContextListener {

    public static final int COPPER_TABLE_COUNT = 8;

    //Lower case for postgres.Might be upper case for other databases
    public static final String COPPER_TABLE_PATTERN = "cop_%";

    private static final Logger logger = LoggerFactory.getLogger(SchemaChecker.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Context ctx = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:global/CopperDataSource");
            Connection con = ds.getConnection();
            DatabaseMetaData meta = con.getMetaData();
            ResultSet res = meta.getTables(null, null, COPPER_TABLE_PATTERN,
                    new String[]{"TABLE"});
            List tables = new ArrayList<String>();
            while (res.next()) {
                tables.add(res.getString("table_name"));
            }
            if (tables.isEmpty()) {
                createCopperSchema(con);
            } else if (tables.size() < COPPER_TABLE_COUNT) {
                throw new IOException("Incomplete copper database schema found:\n" + tables.toString() + "\nPlease remove db container instance.");
            }
        } catch (Exception e) {
            logger.error("Error in copper database schema check.", e);
        }
    }

    private void createCopperSchema(Connection con) throws SQLException {
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("create-schema.sql");
        if (in != null) {
            Scanner s = new Scanner(in).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            logger.warn("No copper database schema found!");
            logger.info("Creating copper database schema using resource file:create-schema.sql");
            logger.info(result);
            Statement st = con.createStatement();
            st.execute(result);
            logger.info("Copper database schema created");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
