package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.scooter.Courier.Courier;
import ru.scooter.Courier.CourierClient;
import ru.scooter.Courier.CourierCredentials;
import ru.scooter.Courier.CourierGenerator;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    private CourierClient courierClient;
    private String courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
    }

    @After
    public void cleanUp() {
        if (courierId != null) courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void courierCanBeLogin() {
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));
        loginResponse.assertThat().statusCode(SC_OK).body("id", notNullValue());
        courierId = loginResponse.extract().path("id").toString();
    }

    @Test
    @DisplayName("Курьер не может авторизоваться без логина")
    public void courierCannotLoginWithoutLogin() {
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).extract().path("id").toString();
        courier.setLogin("");
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));
        loginResponse.assertThat().statusCode(SC_BAD_REQUEST).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Курьер не может авторизоваться без пароля")
    public void courierCannotLoginWithoutPassword() {
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).extract().path("id").toString();
        courier.setPassword("");
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));
        loginResponse.assertThat().statusCode(SC_BAD_REQUEST).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Несуществующий курьер не может авторизоваться")
    public void cannotLoginWithNonExistentCourier() {
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));
        loginResponse.assertThat().statusCode(SC_NOT_FOUND).body("message", equalTo("Учетная запись не найдена"));
    }

}
