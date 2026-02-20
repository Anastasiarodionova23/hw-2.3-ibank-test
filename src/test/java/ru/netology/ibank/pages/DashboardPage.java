package ru.netology.ibank.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {

    public DashboardPage() {
        $("h2").shouldHave(text("Личный кабинет"));
    }

    public void shouldBeVisible() {
        $("[data-test-id='dashboard']").shouldBe(visible);
    }
}