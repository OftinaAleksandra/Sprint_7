package ru.scooter.Courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(2, 10);
        final String password = RandomStringUtils.randomAlphabetic(2, 10);
        final String firstName = RandomStringUtils.randomAlphabetic(1, 10);
        return new Courier(login, password, firstName);
    }

    public static Courier getDefault() {
        return new Courier("loginAa", "12345", "firstName");
    }
}
