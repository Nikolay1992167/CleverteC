package ru.clevertec.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import liquibase.command.CommandScope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import ru.clevertec.util.YamlUtil;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@WebListener
public class LiquibaseInitializer implements ServletContextListener {

    private Database database;

    /**
     *�������������� ����� contextInitialized, ����� ��������� ���������� ���� ������ � ������� Liquibase.
     *@param event ������ ServletContextEvent, ���������� ���������� � ��������� ��������
     */

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try (JdbcConnection jdbcConnection = new JdbcConnection(getDatabaseConnection());
             ClassLoaderResourceAccessor classLoaderResourceAccessor = new ClassLoaderResourceAccessor()) {
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            new CommandScope("update")
                    .addArgumentValue("changeLogFile", "db/changelog/db.changelog-master.yaml")
                    .addArgumentValue("resourceAccessor", classLoaderResourceAccessor)
                    .addArgumentValue("database", database)
                    .execute();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * ���������� ������ Connection, �������������� ���������� � ����� ������ PostgresSQL.
     *
     * @return ������ Connection, �������������� ���������� � ����� ������ PostgresSQL
     * @throws SQLException ���� ��������� ������ ��� ������ � ����� ������
     */
    private Connection getDatabaseConnection() throws SQLException {
        Map<String, String> postgresqlMap = new YamlUtil().getYamlMap().get("postgresql");
        String url = postgresqlMap.get("url");
        String user = postgresqlMap.get("user");
        String password = postgresqlMap.get("password");
        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return DriverManager.getConnection(url, user, password);
    }
}
