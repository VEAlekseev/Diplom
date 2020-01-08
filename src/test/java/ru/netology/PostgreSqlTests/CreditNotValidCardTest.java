package ru.netology.PostgreSqlTests;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.CreditModel;
import ru.netology.data.DBUtils;
import ru.netology.data.OrderModel;
import ru.netology.page.FormPage;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreditNotValidCardTest {
    private FormPage formPage;

    @BeforeEach
    void setUpPage() {
        formPage = new FormPage();
    }

    @Test
    void first_successfulFormFilling() {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("01");
        formPage.setCardYear("22");
        formPage.setCardOwner("Ivanov Ivan");
        formPage.setCardCVV("111");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
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
            DBUtils.clearAllDataPSQL();
        }
    }


}