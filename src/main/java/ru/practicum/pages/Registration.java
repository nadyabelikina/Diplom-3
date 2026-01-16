package ru.practicum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Registration {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameField = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By emailField = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By passwordErrorText = By.xpath(".//p[@class='input__error text_type_main-default']");
    private final By loginLink = By.xpath(".//a[text()='Войти']");

    public Registration(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step("Вводим имя: {name}")
    public Registration setName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        return this;
    }

    @Step("Вводим email: {email}")
    public Registration setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        return this;
    }

    @Step("Вводим пароль")
    public Registration setPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        return this;
    }

    @Step("Кликаем 'Зарегистрироваться'")
    public void clickRegisterButton() {
       wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    @Step("Получаем текст ошибки под паролем")
    public String getTextException() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordErrorText));
        return error.getText();
    }

    @Step("Переход на форму логина")
    public void clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }
}