package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.scooter.Courier.Courier;
import ru.scooter.Courier.CourierClient;
import ru.scooter.Courier.CourierCredentials;
import ru.scooter.Courier.CourierGenerator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class CreateCourierTest {
    private Courier courier;
    private CourierClient courierClient;
    private String courierId;

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
    @DisplayName("Успешное создание курьера")
    @Description("Курьер создается при корректных данных")
    public void courierCreatedSuccessTest() {
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id").toString();
        createResponse.assertThat().statusCode(SC_CREATED).body("ok", is(true));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Нельзя создать двух одинаковых курьеров")
    public void cannotCreateTwoIdenticalCouriersTest() {
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.loginCourier(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id").toString();
        ValidatableResponse createIdenticalCourierResponse = courierClient.createCourier(courier);
        createIdenticalCourierResponse.assertThat().statusCode(SC_CONFLICT).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

}
