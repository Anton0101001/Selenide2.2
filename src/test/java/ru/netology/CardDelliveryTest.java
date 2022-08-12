package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDelliveryTest {

    String orderDay(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test
    public void shouldRegByAccount() {

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Санкт-Петербург");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(orderDay(3));
        form.$("[data-test-id='name'] input").setValue("Сидоров-петров Иван");
        form.$("[data-test-id='phone'] input").setValue("+79001234567");
        form.$("[data-test-id='agreement']").click();
        $x("//span[text()=\"Забронировать\"]").click();
        $("[data-test-id=notification]").shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").shouldHave(Condition.text("Встреча успешно забронирована на " + orderDay(3)));

    }
}
