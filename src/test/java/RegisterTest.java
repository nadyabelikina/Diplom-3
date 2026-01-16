//package test;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.constants.Endpoints;
import ru.practicum.generate.UserDataFactory;
import ru.practicum.steps.UserStep;
import io.qameta.allure.Step;


import static org.junit.Assert.assertEquals;


public class RegisterTest extends BaseTest {
    private static final String EXPECTED_LOGIN_URL = Endpoints.BASE_URI + "/login";
    private static final String EXPECTED_ERROR_TEXT = "Некорректный пароль";
    private String tempToken;

    @Before
    public void initUser() { user = UserDataFactory.generateValidUser(); }

    @After
    public void deleteCreatedUser() {
        if (tempToken == null) {
            tempToken = userStep.login(user).extract().path("accessToken");
        }
        if (tempToken != null) userStep.delete(tempToken);
    }

    @Test
    @DisplayName("Успешная регистрация пользователя через UI")
    @Description("Проверяет регистрацию и редирект на страницу логина")
    public void RegisterUserWithValidData() {
        goToRegisterPage();
        String name = user.getName();
        String email = user.getEmail();
        String pass = user.getPassword();


        registration.setName(name)
                .setEmail(email)
                .setPassword(pass)
                .clickRegisterButton();

        login.waitForLoginPageToLoad();

        assertEquals(EXPECTED_LOGIN_URL, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Ошибка при регистрации с паролем < 6 символов")
    @Description("Проверяет сообщение об ошибке при коротком пароле")
    public void UnRegisterUserWithShortPassword() {
        goToRegisterPage();
        registration.setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword("test")
                .clickRegisterButton();
        String err = registration.getTextException();
        assertEquals(EXPECTED_ERROR_TEXT, err);
    }

    @Step("Открываем форму регистрации через Личный кабинет")
    private void goToRegisterPage() {
        main.clickPersonalAccount();
        login.waitForLoginPageToLoad();
        login.clickRegisterLink();
    }
}