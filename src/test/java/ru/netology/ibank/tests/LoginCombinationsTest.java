package ru.netology.ibank.tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.ibank.pages.LoginPage;
import ru.netology.ibank.utils.TestUserClient;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class LoginCombinationsTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromiumdriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    static Stream<LoginTestData> testDataProvider() {
        return Stream.of(
                // 1. Счастливый путь (всё ок)
                new LoginTestData(true, "active", false, false, true),
                // 2. Заблокированный пользователь
                new LoginTestData(true, "blocked", false, false, false),
                // 3. Несуществующий пользователь
                new LoginTestData(false, null, false, false, false),
                // 4. Неверный логин
                new LoginTestData(true, "active", true, false, false),
                // 5. Неверный пароль
                new LoginTestData(true, "active", false, true, false),
                // 6. Всё неверно
                new LoginTestData(true, "active", true, true, false),
                // 7. Ожидает подтверждения
                new LoginTestData(true, "pending", false, false, false)
        );
    }

    @ParameterizedTest(name = "Тест: {0}")
    @MethodSource("testDataProvider")
    public void testLoginCombinations(LoginTestData data) {
        // Генерируем уникальные данные
        String uniqueId = String.valueOf(System.currentTimeMillis());
        String validLogin = "user" + uniqueId + "@bank.ru";
        String validPassword = "Pass" + uniqueId;

        // Шаг 1: Создаём пользователя через тестовый режим (если нужно)
        if (data.userExists) {
            TestUserClient.createTestUser(validLogin, validPassword, data.status);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Шаг 2: Формируем данные для входа
        String login = data.invalidLogin ? "wrong@bank.ru" : validLogin;
        String password = data.invalidPassword ? "wrongPass" : validPassword;

        // Шаг 3: Выполняем вход
        loginPage.open();
        loginPage.enterLogin(login);
        loginPage.enterPassword(password);
        loginPage.submit();

        // Шаг 4: Проверяем результат
        if (data.expectedSuccess) {
            try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            assertTrue(loginPage.isLoggedIn(), "Должны успешно войти: " + data);
        } else {
            assertTrue(loginPage.hasError(), "Должна быть ошибка: " + data);
        }
    }
}