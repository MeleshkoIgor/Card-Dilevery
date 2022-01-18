package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDileveryTest {

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(5);

    @Test
    void shouldSendSuccessfulRequest() {
        //Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void shouldSendEmptyForm() {
        open("http://localhost:9999/");
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendEmptyFormCity() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendEmptyFormName() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendEmptyFormPhone() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldSendEmptyFormCheckbox() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $(".button").click();
        $("[data-test-id='agreement'] .checkbox__text")
                .shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
    @Test
    void shouldSendFormInvalidName() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Ivanov Ivan");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldSendFormNameWithPatronymic() {
        //Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
    @Test
    void shouldSendFormCityEnglishCity() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("London");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }
    @Test
    void shouldSendFormNameWith2Letters() {
        //Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE), planningDate);
        $("[data-test-id='name'] input").setValue("И И");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement'] .checkbox__text").click();
        $(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldHave(Condition.text("Успешно!"),
                Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}

