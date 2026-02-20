package ru.netology.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import ru.netology.test.data.DataGenerator;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    static void setUp() {
        // Подготовительные действия при необходимости
    }

    @Test
    @DisplayName("1. Успешное создание нового пользователя")
    void shouldCreateNewUserSuccessfully() {
        DataGenerator.UserInfo user = DataGenerator.generateRandomUser();

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("2. Создание пользователя с существующим логином (перезапись)")
    void shouldOverwriteExistingUser() {
        DataGenerator.UserInfo firstUser = DataGenerator.generateUser("vasya", "password123", "active");
        DataGenerator.UserInfo secondUser = DataGenerator.generateUser("vasya", "newpassword", "blocked");

        given()
                .spec(requestSpec)
                .body(firstUser)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

        given()
                .spec(requestSpec)
                .body(secondUser)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("5. Создание пользователя с невалидным статусом")
    void shouldNotCreateUserWithInvalidStatus() {
        DataGenerator.UserInfo user = new DataGenerator.UserInfo(
                "valid_login",
                "validPassword123",
                "invalid_status"
        );

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(500);
    }

    @Test
    @DisplayName("6. Создание нескольких разных пользователей")
    void shouldCreateMultipleUsers() {
        DataGenerator.UserInfo user1 = DataGenerator.generateUser("user1", "pass1", "active");
        DataGenerator.UserInfo user2 = DataGenerator.generateUser("user2", "pass2", "blocked");
        DataGenerator.UserInfo user3 = DataGenerator.generateUser("user3", "pass3", "active");

        given()
                .spec(requestSpec)
                .body(user1)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

        given()
                .spec(requestSpec)
                .body(user2)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

        given()
                .spec(requestSpec)
                .body(user3)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("7. Попытка создания пользователя без тела запроса")
    void shouldNotCreateUserWithoutBody() {
        given()
                .spec(requestSpec)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(500);
    }

    @Test
    @DisplayName("8. Попытка создания пользователя с пустыми полями")
    void shouldNotCreateUserWithEmptyFields() {
        DataGenerator.UserInfo user = DataGenerator.generateUser("", "", "");

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(500);
    }
}
