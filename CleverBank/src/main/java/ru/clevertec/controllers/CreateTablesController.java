package ru.clevertec.controllers;

import ru.clevertec.dao.db.DatabaseConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@WebServlet("/db")
public class CreateTablesController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection;
        try {
            connection = DatabaseConnection.initializeDatabase();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File("E:\\Java projects\\Clevertec\\CleverBank\\src\\main\\resources\\dbinit\\create_sql.sql");
        List<String> lines = Files.readAllLines(file.toPath());
        String createSql = String.join("\n", lines);

        String[] createQueries = createSql.split(";");
        try (Statement stmt = connection.createStatement()) {
            for (String query : createQueries) {
                stmt.execute(query);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String insertSql = Files.readString(Paths.get("E:\\Java projects\\Clevertec\\CleverBank\\src\\main\\resources\\dbinit\\insert_sql.sql"));
        String[] insertQueries = insertSql.split(";");
        try (Statement stmt = connection.createStatement()) {
            for (String query : insertQueries) {
                stmt.execute(query);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
