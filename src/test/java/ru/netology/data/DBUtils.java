package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DBUtils {
    private static Connection connection;
    private static String creditSQLQuery = "SELECT * FROM credit_request_entity WHERE created IN (SELECT max(created) " +
            "FROM credit_request_entity);";
    private static String orderSQLQuery = "SELECT * FROM order_entity WHERE created IN (SELECT max(created) " +
            "FROM order_entity);";
    private static String paymentSQLQuery = "SELECT * FROM payment_entity WHERE created IN (SELECT max(created) " +
            "FROM payment_entity);";

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


    public static void checkLastPaymentStatus(String status) throws SQLException {
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        DBUtils.getUrl(), "app", "pass"
                );
        ) {
            val orderRow = runner.query(conn, orderSQLQuery, new BeanHandler<>(OrderModel.class));
            val paymentRow = runner.query(conn, paymentSQLQuery, new BeanHandler<>(PaymentModel.class));
            assertNotNull(orderRow);
            assertNotNull(paymentRow);
            assertEquals(status, paymentRow.status, "Transaction status should be as");
            assertEquals(4500000, paymentRow.amount, "Transaction amount should be as");
            assertEquals(paymentRow.transaction_id, orderRow.payment_id, "Payment and Order IDs are not equal");
        }
    }

    public static void checkLastCreditStatus(String status) throws SQLException {
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        DBUtils.getUrl(), "app", "pass"
                );
        ) {
            val orderRow = runner.query(conn, orderSQLQuery, new BeanHandler<>(OrderModel.class));
            val creditRow = runner.query(conn, creditSQLQuery, new BeanHandler<>(CreditModel.class));
            assertNotNull(orderRow);
            assertNotNull(creditRow);
            assertEquals(status, creditRow.status, "Credit status should be as");
            assertEquals(creditRow.bank_id, orderRow.payment_id, "Credit and Order IDs are not equal");
        }
    }
}
