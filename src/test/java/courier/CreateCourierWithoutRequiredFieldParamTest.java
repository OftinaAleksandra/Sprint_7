package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.scooter.Courier.Courier;
import ru.scooter.Courier.CourierClient;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)

public class CreateCourierWithoutRequiredFieldParamTest {
    private final String login;
    private final String password;
    private String firstName;
    private CourierClient courierClient;
    private Courier courier;

    public CreateCourierWithoutRequiredFieldParamTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters
    public static Object[][] getCourierData() {
        return new Object[][]{
                {null, RandomStringUtils.randomAlphabetic(2, 10), RandomStringUtils.randomAlphabetic(1, 10)},
                {RandomStringUtils.randomAlphabetic(2, 10), null, RandomStringUtils.randomAlphabetic(1, 10)},
                {RandomStringUtils.randomAlphabetic(2, 10), RandomStringUtils.randomAlphabetic(2, 10), null},
                {null, null, null},
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier(login, password, firstName);
    }

    @Test
    @DisplayName("Нелья создать курьера без обязательных полей")
    public void courierNotCreatedWithoutRequiredFieldsTest() {
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        createResponse.assertThat().statusCode(SC_BAD_REQUEST).body("message", is("Недостаточно данных для создания учетной записи"));
    }
}
