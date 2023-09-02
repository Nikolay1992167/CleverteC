package ru.clevertec.dao.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection initializeDatabase() throws SQLException, ClassNotFoundException, IOException {
        DatabaseConfig settings = new DatabaseConfig();
        String dbDriver = settings.getDbDriver();
        String dbURL = settings.getUrl();
        String dbUsername = settings.getUser();
        String dbPassword = settings.getPassword();
        Class.forName(dbDriver);
        return DriverManager.getConnection(dbURL, dbUsername, dbPassword);
    }
}
