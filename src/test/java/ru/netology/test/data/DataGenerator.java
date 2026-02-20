package ru.netology.test.data;

import lombok.Value;

public class DataGenerator {

    private DataGenerator() {}

    public static UserInfo generateRandomUser() {
        return new UserInfo(
                "vasya",
                "password123",
                "active"
        );
    }

    public static UserInfo generateUser(String login, String password, String status) {
        return new UserInfo(login, password, status);
    }

    public static String[] getInvalidLogins() {
        return new String[]{
                "admin)",  // спецсимвол )

        };
    }

    public static String[] getInvalidPasswords() {
        return new String[]{
                "password with space",  // пробел
                "pass)",   // спецсимвол )
        };
    }

    @Value
    public static class UserInfo {
        String login;
        String password;
        String status;
    }
}