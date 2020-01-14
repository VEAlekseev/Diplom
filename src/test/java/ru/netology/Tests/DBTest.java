package ru.netology.Tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DBUtils;

import java.sql.SQLException;

public class DBTest extends BaseTest {


    @Test
    @DisplayName("Тест дебетовой карты с проверкой в БД")
    void debitValidCardTest() throws SQLException {
        formPage.buyForYourMoney();
        formPage.validCardData();
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkLastPaymentStatus("APPROVED");
    }

    @Test
    @DisplayName("Тест невалидной дебетовой карты с проверкой в БД")
    void debitNotValidCardTest() throws SQLException {
        formPage.buyForYourMoney();
        formPage.notValidCardData();
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkLastPaymentStatus("DECLINED");
    }

    @Test
    @DisplayName("Тест валидной кредитной карты с проверкой в БД")
    void creditValidCardTest() throws SQLException {
        formPage.buyOnCredit();
        formPage.validCardData();
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkLastCreditStatus("APPROVED");
    }

    @Test
    @DisplayName("Тест не валидной кредитной карты с проверкой в БД")
    void creditNotValidCardTest() throws SQLException {
        formPage.buyOnCredit();
        formPage.notValidCardData();
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkLastCreditStatus("DECLINED");
    }
}
