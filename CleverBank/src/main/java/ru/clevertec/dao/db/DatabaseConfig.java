package ru.clevertec.dao.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class DatabaseConfig {
    private String dbDriver;
    private String url;
    private String user;
    private String password;

    public String getDbDriver() {
        return dbDriver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
