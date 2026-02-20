package ru.netology.ibank.data;

import com.github.javafaker.Faker;
import lombok.Value;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final Faker faker = new Faker();
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private DataGenerator() {}

    public static String generateLogin() {
        return faker.name().username();
    }

    public static String generatePassword() {
        return faker.internet().password();
    }

    public static RegistrationInfo generateActiveUser() {
        return new RegistrationInfo(generateLogin(), generatePassword(), "active");
    }

    public static RegistrationInfo generateBlockedUser() {
        return new RegistrationInfo(generateLogin(), generatePassword(), "blocked");
    }

    public static RegistrationInfo registerUser(RegistrationInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
        return user;
    }

    @Value
    public static class RegistrationInfo {
        String login;
        String password;
        String status;
    }
}