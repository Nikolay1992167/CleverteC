package ru.clevertec.exception;

import java.sql.SQLException;

public class ResourceSqlException extends IllegalStateException {
    public ResourceSqlException() {
        super("Data base is not available");
    }

    public ResourceSqlException(String message) {
        super(message);
    }

    public ResourceSqlException(String message, SQLException exception) {
    }
}
