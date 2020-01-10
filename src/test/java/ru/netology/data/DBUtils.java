package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtils {
    private static Connection connection;

    public static void clearAllData() throws SQLException {
        val runner = new QueryRunner();
        connection = DriverManager.getConnection(getUrl(), "app", "pass");
        runner.update(connection, "DELETE FROM credit_request_entity;");
        runner.update(connection, "DELETE FROM payment_entity;");
        runner.update(connection, "DELETE FROM order_entity;");
    }

    public static String getUrl() {
        return System.getProperty("db.url");
    }
}
