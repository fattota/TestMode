package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldBe(visible).shouldHave(exactText("Личный кабинет"));
    }

    @Test
    public void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible).shouldHave(exactText("Ошибка Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGetErrorIfBlockedUser() {

        var registeredUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldGetErrorWithWrongLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var invalidLogin = DataGenerator.getRandomLogin();
        $("[data-test-id='login'] input").setValue(invalidLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible).shouldHave(exactText("Ошибка Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGetErrorIfInvalidPassword() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var invalidPassword = DataGenerator.getRandomPassword();
        $("[data-test-id='login'] input").setValue(invalidPassword);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible).shouldHave(exactText("Ошибка Ошибка! Неверно указан логин или пароль"));
    }


}







