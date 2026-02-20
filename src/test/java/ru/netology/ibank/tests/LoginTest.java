package ru.netology.ibank.tests;

import org.junit.jupiter.api.*;
import ru.netology.ibank.data.DataGenerator;
import ru.netology.ibank.pages.LoginPage;

public class LoginTest {
    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage();
    }

    @Test
    @DisplayName("Should successfully login with active user")
    void shouldLoginWithActiveUser() throws InterruptedException {
        var user = DataGenerator.generateActiveUser();
        DataGenerator.registerUser(user);

        loginPage.enterLogin(user.getLogin());
        loginPage.enterPassword(user.getPassword());
        loginPage.submit();

        Thread.sleep(10000);
    }

    @Test
    @DisplayName("Should get error message if user is blocked")
    void shouldGetErrorIfBlocked() {
        var user = DataGenerator.generateBlockedUser();
        DataGenerator.registerUser(user);

        loginPage.enterLogin(user.getLogin());
        loginPage.enterPassword(user.getPassword());
        loginPage.submit();

    }
}