package ru.netology.test;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.CreditModel;
import ru.netology.data.OrderModel;
import ru.netology.data.PaymentModel;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class dataDBTest {
    @Test
    @DisplayName("Проверка на занесение данных в БД при успешной оплате")
    void checkDataIntoTheDatabaseSuccessfulPayment() throws SQLException, NullPointerException {
        val orderSQLQuery = "SELECT * FROM order_entity WHERE created IN (SELECT max(created) FROM order_entity);";
        val paymentSQLQuery = "SELECT * FROM payment_entity WHERE created IN (SELECT max(created) FROM payment_entity);";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/app", "app", "pass"
                );
        ) {
            val orderRow = runner.query(conn, orderSQLQuery, new BeanHandler<>(OrderModel.class));
            val paymentRow = runner.query(conn, paymentSQLQuery, new BeanHandler<>(PaymentModel.class));
            String paymentStatus = paymentRow.getStatus();
            int transactionAmount = paymentRow.getAmount();
            String paymentTransactionId = paymentRow.getTransaction_id();
            String orderTransactionId = orderRow.getPayment_id();
            assertNotNull(orderRow);
            assertNotNull(paymentRow);
            assertEquals("APPROVED", paymentStatus, "Transaction status should be as");
            assertEquals(4500000, transactionAmount, "Transaction amount should be as");
            assertEquals(paymentTransactionId, orderTransactionId, "Payment and Order IDs are not equal");
        }
    }

    @Test
    @DisplayName("Проверка на отсутствие занесение данных в БД при попытке оплаты невалидной картой")
    void checkDataIntoTheDatabaseNotSuccessfulPayment() throws SQLException, NullPointerException {
        val orderSQLQuery = "SELECT * FROM order_entity WHERE created IN (SELECT max(created) FROM order_entity);";
        val paymentSQLQuery = "SELECT * FROM payment_entity WHERE created IN (SELECT max(created) FROM payment_entity);";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/app", "app", "pass"
                );
        ) {
            val orderRow = runner.query(conn, orderSQLQuery, new BeanHandler<>(OrderModel.class));
            val paymentRow = runner.query(conn, paymentSQLQuery, new BeanHandler<>(PaymentModel.class));
            String paymentStatus = paymentRow.getStatus();
            assertNotNull(orderRow);
            assertNotNull(paymentRow);
            assertEquals("APPROVED", paymentStatus, "Transaction status should be as");
        }
    }

    @Test
    @DisplayName("Проверка на занесение данных в БД при успешной оплате Mysql")
    void checkDataIntoTheDatabaseSuccessfulPaymentMysql() throws SQLException, NullPointerException {
        val orderSQLQuery = "SELECT * FROM order_entity WHERE created IN (SELECT max(created) FROM order_entity);";
        val paymentSQLQuery = "SELECT * FROM payment_entity WHERE created IN (SELECT max(created) FROM payment_entity);";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val orderRow = runner.query(conn, orderSQLQuery, new BeanHandler<>(OrderModel.class));
            val paymentRow = runner.query(conn, paymentSQLQuery, new BeanHandler<>(PaymentModel.class));
            String paymentStatus = paymentRow.getStatus();
            int transactionAmount = paymentRow.getAmount();
            String paymentTransactionId = paymentRow.getTransaction_id();
            String orderTransactionId = orderRow.getPayment_id();
            assertNotNull(orderRow);
            assertNotNull(paymentRow);
            assertEquals("APPROVED", paymentStatus, "Transaction status should be as");
            assertEquals(4500000, transactionAmount, "Transaction amount should be as");
            assertEquals(paymentTransactionId, orderTransactionId, "Payment and Order IDs are not equal");
        }
    }

    @Test
    @DisplayName("Проверка на отсутствие занесение данных в БД при попытке оплаты невалидной картой Mysql")
    void checkDataIntoTheDatabaseNotSuccessfulPaymentMysql() throws SQLException, NullPointerException {
        val orderSQLQuery = "SELECT * FROM order_entity WHERE created IN (SELECT max(created) FROM order_entity);";
        val paymentSQLQuery = "SELECT * FROM payment_entity WHERE created IN (SELECT max(created) FROM payment_entity);";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val orderRow = runner.query(conn, orderSQLQuery, new BeanHandler<>(OrderModel.class));
            val paymentRow = runner.query(conn, paymentSQLQuery, new BeanHandler<>(PaymentModel.class));
            String paymentStatus = paymentRow.getStatus();
            assertNotNull(orderRow);
            assertNotNull(paymentRow);
            assertEquals("APPROVED", paymentStatus, "Transaction status should be as");
        }
    }
    @Test
    void second_dataBaseTest() throws SQLException {

        val orderSQLQuery = "SELECT * FROM order_entity WHERE created IN (SELECT max(created) FROM order_entity);";
        val creditSQLQuery = "SELECT * FROM credit_request_entity WHERE created IN (SELECT max(created) FROM credit_request_entity);";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/app", "app", "pass"
                );
        ) {
            val orderRow = runner.query(conn, orderSQLQuery, new BeanHandler<>(OrderModel.class));
            val creditRow = runner.query(conn, creditSQLQuery, new BeanHandler<>(CreditModel.class));
            String creditStatus = creditRow.getStatus();
            String creditTransactionId = creditRow.getBank_id();
            String orderTransactionId = orderRow.getPayment_id();
            assertNotNull(orderRow);
            assertNotNull(creditRow);
            assertEquals("DECLINED", creditStatus, "Credit status should be as");
            assertEquals(creditTransactionId, orderTransactionId, "Credit and Order IDs are not equal");
        }
    }
}