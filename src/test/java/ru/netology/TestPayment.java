package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.sql.SQLException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPayment {

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Успешная покупка утвержденной картой")
    void shouldPayByApprovedCard() throws SQLException {
        open("http://localhost:8080");
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        $$(".input__control").get(0).setValue("4444444444444441");
        $$(".input__control").get(1).setValue("01");
        $$(".input__control").get(2).setValue("22");
        $$(".input__control").get(3).setValue("Иванов Иван");
        $$(".input__control").get(4).setValue("111");
        $$(".button__content").find(exactText("Продолжить")).click();
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 40000);
    }

    @Test
    @DisplayName("Успешная покупка в кредит утвержденной картой")
    void shouldPayCreditByApprovedCard() throws SQLException {
        open("http://localhost:8080");
        $$(".button__text").find(exactText("Купить в кредит")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты")).shouldBe(visible);
        $$(".input__control").get(0).setValue("4444444444444441");
        $$(".input__control").get(1).setValue("01");
        $$(".input__control").get(2).setValue("22");
        $$(".input__control").get(3).setValue("Иванов Иван");
        $$(".input__control").get(4).setValue("111");
        $$(".button__content").find(exactText("Продолжить")).click();
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 40000);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Field.csv", numLinesToSkip = 1)
    void verifyPayField(String numberCard,String mounth,String year,String name,String cvv,
                        String expected,String message) throws SQLException{
        open("http://localhost:8080");
        $$(".button__text").find(exactText("Купить в кредит")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты")).shouldBe(visible);
        $$(".input__control").get(0).setValue(numberCard);
        $$(".input__control").get(1).setValue(mounth);
        $$(".input__control").get(2).setValue(year);
        $$(".input__control").get(3).setValue(name);
        $$(".input__control").get(4).setValue(cvv);
        $$(".button__content").find(exactText("Продолжить")).click();
        $(".input__sub").shouldHave(text(expected));
        String actual = $(".input__sub").innerText();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/Field.csv", numLinesToSkip = 1)
    void verifyCreditField(String numberCard,String mounth,String year,String name,String cvv,
                           String expected,String message) throws SQLException {
        open("http://localhost:8080");
        $$(".button__text").find(exactText("Купить в кредит")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты")).shouldBe(visible);
        $$(".input__control").get(0).setValue(numberCard);
        $$(".input__control").get(1).setValue(mounth);
        $$(".input__control").get(2).setValue(year);
        $$(".input__control").get(3).setValue(name);
        $$(".input__control").get(4).setValue(cvv);
        $$(".button__content").find(exactText("Продолжить")).click();
        $(".input__sub").shouldHave(text(expected));
        String actual = $(".input__sub").innerText();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Отказ покупки не утвержденной картой")
    void shouldPayByNotApprovedCard() throws SQLException {
        open("http://localhost:8080");
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
        $$(".input__control").get(0).setValue("4444444444444442");
        $$(".input__control").get(1).setValue("01");
        $$(".input__control").get(2).setValue("22");
        $$(".input__control").get(3).setValue("Иванов Иван");
        $$(".input__control").get(4).setValue("111");
        $$(".button__content").find(exactText("Продолжить")).click();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 40000);
    }

    @Test
    @DisplayName("Отказ покупки в кредит не утвержденной картой")
    void shouldPayCreditByNotApprovedCard() throws SQLException {
        open("http://localhost:8080");
        $$(".button__text").find(exactText("Купить в кредит")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты")).shouldBe(visible);
        $$(".input__control").get(0).setValue("4444444444444442");
        $$(".input__control").get(1).setValue("01");
        $$(".input__control").get(2).setValue("22");
        $$(".input__control").get(3).setValue("Иванов Иван");
        $$(".input__control").get(4).setValue("111");
        $$(".button__content").find(exactText("Продолжить")).click();
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 40000);
    }

}