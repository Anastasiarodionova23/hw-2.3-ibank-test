package ru.netology.ibank.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestUserClient {
    private static final String BASE_URL = "http://localhost:9999/api";

    public static Response createTestUser(String login, String password, String status) {
        String jsonBody = String.format(
                "{\"login\":\"%s\", \"password\":\"%s\", \"status\":\"%s\"}",
                login, password, status
        );

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(jsonBody)
                .post("/system/users");

        System.out.println("🔵 Создание пользователя: " + login);
        System.out.println("🔵 Статус ответа: " + response.getStatusCode());
        System.out.println("🔵 Тело ответа: " + response.getBody().asString());

        return response;
    }
}