package order;

import ru.scooter.Order.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderListTest() {
        ValidatableResponse getOrderListResponse = orderClient.getOrders();
        getOrderListResponse.assertThat().statusCode(SC_OK).body("orders", notNullValue());
    }
}
