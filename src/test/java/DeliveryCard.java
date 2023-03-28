package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class DeliveryCard {
    private String generateData(int addDay, String patern) {
        return LocalDate.now().plusDays(addDay).format(DateTimeFormatter.ofPattern(patern));
    }
    @Test
    void deliveryTestV1() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ставрополь");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.LEFT_SHIFT, Keys.HOME), Keys.DELETE);
        String currentData = generateData(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(currentData);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79614402259");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification].notification_visible").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно! Встреча успешно забронирована на " + currentData));
    }

    @Test
    void deliveryTestV2() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ст");
        $$(".menu-item__control").find(exactText("Ставрополь")).click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.LEFT_SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").click();
        int data = 3;
        int dataDelivery = 7;
        if (!generateData(data,"MM").equals(generateData(dataDelivery, "MM"))){
            $("[data-step='1']").click();
        }
        $$(".calendar__day").find(exactText(generateData(dataDelivery,"dd"))).click();
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79614402259");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification].notification_visible").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Успешно! Встреча успешно забронирована на " + generateData(dataDelivery, "dd.MM.yyyy")));
    }


}
