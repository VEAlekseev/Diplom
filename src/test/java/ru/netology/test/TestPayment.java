package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.data.DBUtils;
import ru.netology.page.FormPage;

import java.sql.SQLException;

public class TestPayment {
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
        DBUtils.clearAllDataMySQL();
        DBUtils.clearAllDataPSQL();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Успешная покупка утвержденной картой")
    void shouldPayByApprovedCard() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("22");
        formPage.setCardOwner("Ivanov Ivan");
        formPage.setCardCVV("111");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }

    @Test
    @DisplayName("Успешная покупка в кредит утвержденной картой")
    void shouldPayCreditByApprovedCard() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("22");
        formPage.setCardOwner("Ivanov Ivan");
        formPage.setCardCVV("111");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Field.csv", numLinesToSkip = 1)
    void verifyPayField(String numberCard, String mounth, String year, String name, String cvv,
                        String expected, String message) throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber(numberCard);
        formPage.setCardMonth(mounth);
        formPage.setCardYear(year);
        formPage.setCardOwner(name);
        formPage.setCardCVV(cvv);
        formPage.pushСontinueButton();
        formPage.checkExpected(expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Field.csv", numLinesToSkip = 1)
    void verifyCreditField(String numberCard, String mounth, String year, String name, String cvv,
                           String expected, String message) throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber(numberCard);
        formPage.setCardMonth(mounth);
        formPage.setCardYear(year);
        formPage.setCardOwner(name);
        formPage.setCardCVV(cvv);
        formPage.pushСontinueButton();
        formPage.checkExpected(expected);
    }


    @Test
    @DisplayName("Отказ покупки не утвержденной картой")
    void shouldPayByNotApprovedCard() throws SQLException {
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
    @DisplayName("Отказ покупки в кредит не утвержденной картой")
    void shouldPayCreditByNotApprovedCard() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("01");
        formPage.setCardYear("22");
        formPage.setCardOwner("Ivanov Ivan");
        formPage.setCardCVV("111");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }
}