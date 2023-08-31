package ru.clevertec.dao.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static DatabaseConfig readDatabaseConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File file = new File("E:\\Java projects\\Clevertec\\CleverBank\\src\\main\\resources\\app.yml");
        return mapper.readValue(file, DatabaseConfig.class);
    }

    public static Connection initializeDatabase() throws SQLException, ClassNotFoundException, IOException {
        DatabaseConfig settings = readDatabaseConfig();
        String dbDriver = settings.getDbDriver();
        String dbURL = settings.getUrl();
        String dbUsername = settings.getUser();
        String dbPassword = settings.getPassword();

        Class.forName(dbDriver);
        return DriverManager.getConnection(dbURL, dbUsername, dbPassword);
    }
}
