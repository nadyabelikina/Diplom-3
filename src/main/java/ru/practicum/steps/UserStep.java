package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.practicum.constants.Endpoints;
import ru.practicum.model.LoginField;
import ru.practicum.model.UserData;

import static io.restassured.RestAssured.given;


public class UserStep {
    @Step("Создание пользователя через API")
    public ValidatableResponse create(UserData user) {
        return given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(Endpoints.REGISTER)
                .then();
    }

    @Step("Авторизация пользователя через API")
    public ValidatableResponse login(UserData user) {
        LoginField req = new LoginField(user.getEmail(), user.getPassword());
        return given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .body(req)
                .when()
                .post(Endpoints.LOGIN)
                .then();
    }

    @Step("Удаление пользователя через API")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .baseUri(Endpoints.BASE_URI)
                .header("Authorization", accessToken)
                .when()
                .delete(Endpoints.DELETE_USER)
                .then();
    }
}