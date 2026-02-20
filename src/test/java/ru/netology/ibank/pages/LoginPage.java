package ru.netology.ibank.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    private SelenideElement loginInput = $("[data-test-id='login'] input");
    private SelenideElement passwordInput = $("[data-test-id='password'] input");
    private SelenideElement submitButton = $("[data-test-id='action-login']");

    public LoginPage() {
        open("http://localhost:9999");
    }

    public void enterLogin(String login) {
        loginInput.setValue(login);
    }

    public void enterPassword(String password) {
        passwordInput.setValue(password);
    }

    public void submit() {
        submitButton.click();
    }

    public DashboardPage validLogin(String login, String password) {
        enterLogin(login);
        enterPassword(password);
        submit();
        return new DashboardPage();
    }

    public void shouldHaveError(String expectedMessage) {
        $("[data-test-id='error-notification']")
                .shouldHave(text(expectedMessage));
    }
}