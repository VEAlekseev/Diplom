package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.models.CardModel;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormPage {
    private static String host = System.getProperty("host");

    SelenideElement cardNumber = $(byText("Номер карты")).parent().$(".input__control");
    SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    SelenideElement year = $(byText("Год")).parent().$(".input__control");
    SelenideElement cardOwner = $(byText("Владелец")).parent().$(".input__control");
    SelenideElement cvcOrCvvNumber = $(byText("CVC/CVV")).parent().$(".input__control");

    public void buyForYourMoney() {
        open(host);
        $$(".button__content").find(exactText("Купить")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте")).shouldBe(visible);
    }

    public void buyOnCredit() {
        open(host);
        $$(".button__content").find(exactText("Купить в кредит")).click();
        $$(".heading_theme_alfa-on-white").find(exactText("Кредит по данным карты")).shouldBe(visible);
    }

    public void checkMessageSuccess() {
        $$(".notification__title").find(exactText("Успешно")).waitUntil(visible, 75000);
        $(withText("Успешно")).shouldBe(Condition.appear);
    }

    public void checkMessageError() {
        $$(".notification__title").find(exactText("Ошибка")).waitUntil(visible, 75000);
        $(withText("Ошибка")).shouldBe(Condition.appear);
    }

    public void comparisonOfExpectedAndActualResult(String expected) {
        $(".input__sub").shouldHave(text(expected));
        String actual = $(".input__sub").innerText();
        assertEquals(expected, actual);
    }

    public void setCardNumber(String number) {
        cardNumber.setValue(number);
    }

    public void setCardMonth(String months) {
        month.setValue(months);
    }

    public void setCardYear(String years) {
        year.setValue(years);
    }

    public void setCardOwner(String owner) {
        cardOwner.setValue(owner);
    }

    public void setCardCVV(String cvv) {
        cvcOrCvvNumber.setValue(cvv);
    }

    public void pushСontinueButton() {
        $$(".button__content").find(exactText("Продолжить")).click();
    }

    public void validCardData(){
        setCardNumber("4444444444444441");
        setCardMonth("01");
        setCardYear("22");
        setCardOwner("Ivanov Ivan");
        setCardCVV("111");
    }

    public void notValidCardData(){
        setCardNumber("4444444444444442");
        setCardMonth("01");
        setCardYear("22");
        setCardOwner("Ivanov Ivan");
        setCardCVV("111");
    }
}