import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

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


}
