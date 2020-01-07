package ru.netology.postgreSqlTests;

import com.codeborne.selenide.Condition;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import lombok.val;
import ru.netology.data.OrderModel;
import ru.netology.data.PaymentModel;

public class DebitValidCardTest {

    @Test
    void first_successfulFormFilling() {
        open("http://localhost:8080");
        $("button:first-of-type").click();
        $("fieldset .input__control:first-of-type").setValue("4444444444444441").sendKeys(Keys.chord(Keys.TAB));
        $(".input__control:focus").setValue("12").sendKeys(Keys.chord(Keys.TAB));
        $(".input__control:focus").setValue("20").sendKeys(Keys.chord(Keys.TAB));
        $(".input__control:focus").setValue("Ivanov Ivan").sendKeys(Keys.chord(Keys.TAB));
        $(".input__control:focus").setValue("333");
        $(withText("Продолжить")).click();
        $(withText("Успешно")).waitUntil(Condition.visible, 40000);
    }

    @Test
    void second_dataBaseTest() throws SQLException {
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
}