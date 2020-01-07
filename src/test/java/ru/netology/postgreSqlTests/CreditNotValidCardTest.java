package ru.netology.postgreSqlTests;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.CreditModel;
import ru.netology.data.OrderModel;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreditNotValidCardTest {

    @Test
    void first_successfulFormFilling() {
        open("http://localhost:8080");
        $("button:last-of-type").click();
        $("fieldset .input__control:first-of-type").setValue("4444444444444442").sendKeys(Keys.chord(Keys.TAB));
        $(".input__control:focus").setValue("12").sendKeys(Keys.chord(Keys.TAB));
        $(".input__control:focus").setValue("20").sendKeys(Keys.chord(Keys.TAB));
        $(".input__control:focus").setValue("Ivanov Ivan").sendKeys(Keys.chord(Keys.TAB));
        $(".input__control:focus").setValue("111");
        $(withText("Продолжить")).click();
        $(withText("Ошибка")).waitUntil(Condition.visible, 40000);
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