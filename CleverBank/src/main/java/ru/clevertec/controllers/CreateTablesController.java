package ru.clevertec.controllers;

import ru.clevertec.dao.db.DatabaseConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

@WebServlet("/db")
public class CreateTablesController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection;
        try {
            connection = DatabaseConnection.initializeDatabase();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        InputStream createStream = this.getClass().getClassLoader().getResourceAsStream("dbinit/create_sql.sql");
        String createSql = new BufferedReader(new InputStreamReader(createStream)).lines().collect(Collectors.joining("\n"));

        String[] createQueries = createSql.split(";");
        try (Statement stmt = connection.createStatement()) {
            for (String query : createQueries) {
                stmt.execute(query);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        InputStream insertStream = this.getClass().getClassLoader().getResourceAsStream("dbinit/insert_sql.sql");
        String insertSql = new BufferedReader(new InputStreamReader(insertStream)).lines().collect(Collectors.joining("\n"));
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
