package ru.scooter.Courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.scooter.RestClient;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    private static final String COURIER_PATH = "api/v1/courier/";
    private static final String LOGIN_PATH = "api/v1/courier/login/";
    private static final String DELETE_PATH = "api/v1/courier/";

     @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()

                .post(COURIER_PATH)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(credentials)
                .post(LOGIN_PATH)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(String id) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(DELETE_PATH + id)
                .then();
    }
}
