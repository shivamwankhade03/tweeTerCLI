package com.tweetApp.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;
import java.util.Properties;

public class DBConfiguration {
    private static String dbhost = "jdbc:mysql://localhost:3306/tweetapp";
    private static String username = "root";
    private static String password = "root";
    private static Connection conn;
    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }
        @SuppressWarnings("finally")
    public static Connection createNewDBconnection() {
        try  {
            Properties prop = readPropertiesFile("src/main/resources/db.properties");
           String dbhost = prop.getProperty("url");
            String username = prop.getProperty("user");
            String password = prop.getProperty("password");
            conn = DriverManager.getConnection(
                    dbhost, username, password);
        } catch (SQLException e) {
            System.out.println("Cannot create database connection");
            e.printStackTrace();
        } finally {
            return conn;
        }
    }
}