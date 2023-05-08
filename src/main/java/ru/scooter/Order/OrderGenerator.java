package ru.scooter.Order;

import com.github.javafaker.Faker;

public class OrderGenerator {
    static Faker faker = new Faker();
    public static Order getOrderWithoutColor(String[] color) {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().toString();
        String metroStation = "12";
        String phone = "+7 999 99 99";
        int rentTime = 9;
        String deliveryDate = "2023-04-30";
        String comment = "comment";
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
