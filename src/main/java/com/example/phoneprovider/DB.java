package com.example.phoneprovider;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Repository
public class DB {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DB.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find db.properties");
            }

            // Load the properties file
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            var DB_URL = properties.getProperty("db.url");
            var USER = properties.getProperty("db.username");
            var PASS = properties.getProperty("db.password");

            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
