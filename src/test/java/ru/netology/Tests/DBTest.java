package ru.netology.Tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DBUtils;
import ru.netology.models.CardModel;

import java.sql.SQLException;


public class DBTest extends BaseTest {
    private CardModel validCard;
    private CardModel invalidCard;

    @Test
    @DisplayName("Тест дебетовой карты с проверкой в БД")
    void debitValidCardTest() throws SQLException {
        validCard = CardModel.generatedApprovedCard("ru");
        formPage.buyByDebit();
        formPage.fillCardData(validCard);
        formPage.pushContinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkRowPaymentNotNull();
        DBUtils.comparisonExpectedAmountWithActual(4500000);
        DBUtils.checkLastPaymentStatus("APPROVED");
        DBUtils.comparisonIDPaymentAndOrder();
    }

    @Test
    @DisplayName("Тест невалидной дебетовой карты с проверкой в БД")
    void debitNotValidCardTest() throws SQLException {
        invalidCard = CardModel.generatedDeclinedCard("ru");
        formPage.buyByDebit();
        formPage.fillCardData(invalidCard);
        formPage.pushContinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkRowPaymentNotNull();
        DBUtils.comparisonExpectedAmountWithActual(4500000);
        DBUtils.checkLastPaymentStatus("DECLINED");
        DBUtils.comparisonIDPaymentAndOrder();
    }

    @Test
    @DisplayName("Тест валидной кредитной карты с проверкой в БД")
    void creditValidCardTest() throws SQLException {
        validCard = CardModel.generatedApprovedCard("ru");
        formPage.buyOnCredit();
        formPage.fillCardData(validCard);
        formPage.pushContinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkRowCreditNotNull();
        DBUtils.checkLastCreditStatus("APPROVED");
        DBUtils.comparisonIDCreditAndOrder();
    }

    @Test
    @DisplayName("Тест не валидной кредитной карты с проверкой в БД")
    void creditNotValidCardTest() throws SQLException {
        invalidCard = CardModel.generatedDeclinedCard("ru");
        formPage.buyOnCredit();
        formPage.fillCardData(invalidCard);
        formPage.pushContinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkRowCreditNotNull();
        DBUtils.checkLastCreditStatus("DECLINED");
        DBUtils.comparisonIDCreditAndOrder();
    }
}