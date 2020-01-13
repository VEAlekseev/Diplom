package ru.netology.Tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class TestPayment extends BaseTest {

    @Test
    @DisplayName("Успешная покупка утвержденной картой")
    void shouldPayByApprovedCard() {
        formPage.buyForYourMoney();
        formPage.validCardData();
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }

    @Test
    @DisplayName("Успешная покупка в кредит утвержденной картой")
    void shouldPayCreditByApprovedCard() {
        formPage.buyOnCredit();
        formPage.validCardData();
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Field.csv", numLinesToSkip = 1)
    void verifyPayField(String numberCard, String month, String year, String name, String cvv,
                        String expected, String message) {
        formPage.buyForYourMoney();
        formPage.setCardNumber(numberCard);
        formPage.setCardMonth(month);
        formPage.setCardYear(year);
        formPage.setCardOwner(name);
        formPage.setCardCVV(cvv);
        formPage.pushСontinueButton();
        formPage.comparisonOfExpectedAndActualResult(expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Field.csv", numLinesToSkip = 1)
    void verifyCreditField(String numberCard, String month, String year, String name, String cvv,
                           String expected, String message) {
        formPage.buyOnCredit();
        formPage.setCardNumber(numberCard);
        formPage.setCardMonth(month);
        formPage.setCardYear(year);
        formPage.setCardOwner(name);
        formPage.setCardCVV(cvv);
        formPage.pushСontinueButton();
        formPage.comparisonOfExpectedAndActualResult(expected);
    }


    @Test
    @DisplayName("Отказ покупки не утвержденной картой")
    void shouldPayByNotApprovedCard() {
        formPage.buyOnCredit();
        formPage.notValidCardData();
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Отказ покупки в кредит не утвержденной картой")
    void shouldPayCreditByNotApprovedCard() {
        formPage.buyOnCredit();
        formPage.notValidCardData();
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }
}