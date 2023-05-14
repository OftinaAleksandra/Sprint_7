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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DeleteCourierTest {
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
    @DisplayName("Успешное удаление курьера, должно вернуть true")
    public void canDeleteExistCourierTest() {
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).extract().path("id").toString();
        ValidatableResponse deleteCourierResponse = courierClient.deleteCourier(courierId);
        deleteCourierResponse.assertThat().statusCode(SC_OK).body("ok", is(true));
    }

    @Test
    @DisplayName("Удаление несуществующего курьера, должно вернуть ошибку")
    public void cannotDeleteCourierWithoutIdTest() {
        ValidatableResponse deleteCourierResponse = courierClient.deleteCourier("");
        deleteCourierResponse.assertThat().statusCode(SC_NOT_FOUND);
    }
}
