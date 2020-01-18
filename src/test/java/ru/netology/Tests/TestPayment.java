package ru.netology.Tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.models.CardModel;

public class TestPayment extends BaseTest {
    private CardModel validCard;
    private CardModel invalidCard;

    @Test
    @DisplayName("Успешная покупка утвержденной картой")
    void shouldPayByApprovedCard() {
        validCard = CardModel.generatedApprovedCard("ru");
        formPage.buyByDebit();
        formPage.fillCardData(validCard);
        formPage.pushContinueButton();
        formPage.checkMessageSuccess();
    }

    @Test
    @DisplayName("Успешная покупка в кредит утвержденной картой")
    void shouldPayCreditByApprovedCard() {
        validCard = CardModel.generatedApprovedCard("ru");
        formPage.buyOnCredit();
        formPage.fillCardData(validCard);
        formPage.pushContinueButton();
        formPage.checkMessageSuccess();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Field.csv", numLinesToSkip = 1)
    void verifyPayField(String numberCard, String month, String year, String name, String cvv,
                        String expected, String message) {
        formPage.buyByDebit();
        formPage.setCardNumber(numberCard);
        formPage.setCardMonth(month);
        formPage.setCardYear(year);
        formPage.setCardOwner(name);
        formPage.setCardCVV(cvv);
        formPage.pushContinueButton();
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
        formPage.pushContinueButton();
        formPage.comparisonOfExpectedAndActualResult(expected);
    }


    @Test
    @DisplayName("Отказ покупки не утвержденной картой")
    void shouldPayByNotApprovedCard() {
        invalidCard = CardModel.generatedDeclinedCard("ru");
        formPage.buyOnCredit();
        formPage.fillCardData(invalidCard);
        formPage.pushContinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Отказ покупки в кредит не утвержденной картой")
    void shouldPayCreditByNotApprovedCard() {
        invalidCard = CardModel.generatedDeclinedCard("ru");
        formPage.buyOnCredit();
        formPage.fillCardData(invalidCard);
        formPage.pushContinueButton();
        formPage.checkMessageError();
    }
}