package ru.netology.ibank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    private By loginInput = By.cssSelector("[data-test-id='login'] input");
    private By passwordInput = By.cssSelector("[data-test-id='password'] input");
    private By submitButton = By.cssSelector("[data-test-id='action-login']");
    private By dashboard = By.xpath("//*[contains(text(),'Личный кабинет')]");
    private By errorMessage = By.cssSelector("[data-test-id='error-notification']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("http://localhost:9999");
    }

    public void enterLogin(String login) {
        driver.findElement(loginInput).sendKeys(login);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void submit() {
        driver.findElement(submitButton).click();
    }

    public boolean isLoggedIn() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(dashboard));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasError() {
        return !driver.findElements(errorMessage).isEmpty();
    }
}