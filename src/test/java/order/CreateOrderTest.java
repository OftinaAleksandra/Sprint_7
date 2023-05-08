package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Test;
import ru.scooter.Order.Order;
import ru.scooter.Order.OrderClient;
import ru.scooter.Order.OrderGenerator;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderClient orderClient;
    private String track;
    private final String[] color;
    private Order order;

    public CreateOrderTest(String[] color){
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderColor() {
        return new Object[][]{
                {null},
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderGenerator.getOrderWithoutColor(color);
    }

    @After
    public void cleanUp() {
       orderClient.cancel(track);
    }
    @Test
    @DisplayName("Успешное создание заказа")
    public void createOrderTest() {
        ValidatableResponse createOrderResponse = orderClient.create(order);
        createOrderResponse.assertThat().statusCode(SC_CREATED).body("track", notNullValue());
        track = createOrderResponse.extract().path("track").toString();
    }
}
