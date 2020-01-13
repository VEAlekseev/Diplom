package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.models.CreditModel;
import ru.netology.models.OrderModel;
import ru.netology.models.PaymentModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DBUtils {
    private static Connection connection;
    private static String userBD = System.getProperty("userDB");
    private static String password = System.getProperty("password");
    private static String url = System.getProperty("db.url");

    private static String creditSQLQuery = "SELECT * FROM credit_request_entity WHERE created IN (SELECT max(created) " +
            "FROM credit_request_entity);";
    private static String orderSQLQuery = "SELECT * FROM order_entity WHERE created IN (SELECT max(created) " +
            "FROM order_entity);";
    private static String paymentSQLQuery = "SELECT * FROM payment_entity WHERE created IN (SELECT max(created) " +
            "FROM payment_entity);";

    private static Connection getConnection() throws SQLException {
        if (connection == null)
            connection = DriverManager.getConnection(url, userBD, password);
        return connection;
    }

    public static void clearAllData() throws SQLException {
        val runner = new QueryRunner();
        getConnection();
        runner.update(connection, "DELETE FROM credit_request_entity;");
        runner.update(connection, "DELETE FROM payment_entity;");
        runner.update(connection, "DELETE FROM order_entity;");
    }

    public static void checkLastPaymentStatus(String status) throws SQLException {
        val runner = new QueryRunner();
        getConnection();
        val orderRow = runner.query(connection, orderSQLQuery, new BeanHandler<>(OrderModel.class));
        val paymentRow = runner.query(connection, paymentSQLQuery, new BeanHandler<>(PaymentModel.class));
        assertNotNull(orderRow);
        assertNotNull(paymentRow);
        assertEquals(status, paymentRow.status, "Transaction status should be as");
        assertEquals(4500000, paymentRow.amount, "Transaction amount should be as");
        assertEquals(paymentRow.transaction_id, orderRow.payment_id, "Payment and Order IDs are not equal");
    }

    public static void checkLastCreditStatus(String status) throws SQLException {
        val runner = new QueryRunner();
        getConnection();
        val orderRow = runner.query(connection, orderSQLQuery, new BeanHandler<>(OrderModel.class));
        val creditRow = runner.query(connection, creditSQLQuery, new BeanHandler<>(CreditModel.class));
        assertNotNull(orderRow);
        assertNotNull(creditRow);
        assertEquals(status, creditRow.status, "Credit status should be as");
        assertEquals(creditRow.bank_id, orderRow.payment_id, "Credit and Order IDs are not equal");
    }
}