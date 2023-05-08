package ru.scooter.Order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

import ru.scooter.RestClient;

public class OrderClient extends RestClient {
    private static final String ORDER_PATH = "api/v1/orders/";
    private static final String CANCEL_ORDER_PATH = "api/v1/orders/cancel/";


    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }
    @Step("Отмена заказа")
    public ValidatableResponse cancel(String track) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(track)
                .put(CANCEL_ORDER_PATH)
                .then();
    }

}
