package ru.netology.Tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBUtils;
import ru.netology.page.FormPage;

import java.sql.SQLException;

public class DBTest {
    private FormPage formPage;

    @BeforeEach
    void setUpPage() {
        formPage = new FormPage();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void clearAll() throws SQLException {
        DBUtils.clearAllData();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Тест дебетовой карты с проверкой в БД")
    void debitValidCardTest() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("22");
        formPage.setCardOwner("Ivanov Ivan");
        formPage.setCardCVV("111");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkLastPaymentStatus("APPROVED");
    }

    @Test
    @DisplayName("Тест невалидной дебетовой карты с проверкой в БД")
    void debitNotValidCardTest() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("01");
        formPage.setCardYear("22");
        formPage.setCardOwner("Ivanov Ivan");
        formPage.setCardCVV("111");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkLastPaymentStatus("DECLINED");
    }

    @Test
    @DisplayName("Тест валидной кредитной карты с проверкой в БД")
    void creditValidCardTest() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("22");
        formPage.setCardOwner("Ivanov Ivan");
        formPage.setCardCVV("111");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkLastCreditStatus("APPROVED");
    }

    @Test
    @DisplayName("Тест не валидной кредитной карты с проверкой в БД")
    void creditNotValidCardTest() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("01");
        formPage.setCardYear("22");
        formPage.setCardOwner("Ivanov Ivan");
        formPage.setCardCVV("111");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkLastCreditStatus("DECLINED");
    }
}