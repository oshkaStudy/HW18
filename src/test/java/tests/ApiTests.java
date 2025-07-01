package tests;

import io.restassured.response.Response;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specifications.UserSpec.*;

public class ApiTests extends ApiTestBase {

    @Test
    @DisplayName("POST. Создание нового пользователя")
    @Tag("Позитивный")
    void successfulUserCreationTest() {

        UsersBodyModel bodyData = new UsersBodyModel();
        bodyData.setName("neo");
        bodyData.setJob("the chosen one");

        UserCreationResponseModel response = step("Отправка запроса", ()->
            given(basicUsersRequestSpec)
                .body(bodyData)

                .when()
                .post("/users")

                .then()
                .spec(usersSuccessfulCreationResponseSpec)
                .extract().as(UserCreationResponseModel.class));

        step("Проверка содержимого ответа", () -> {
            assertThat(response.getName(), is(bodyData.getName()));
            assertThat(response.getJob(), is(bodyData.getJob()));
            assertThat(response.getId(), matchesRegex("\\d+"));
            assertThat(response.getCreatedAt(), matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
        });
    }

    @Test
    @DisplayName("POST. Создание нового пользователя с пустым body")
    @Tag("Негативный")
    void negativeUserCreationNoBodyTest() {

        UserCreationResponseModel response = step("Отправка запроса", ()->
                given(basicUsersRequestSpec)
                        .body("")

                        .when()
                        .post("/users")

                        .then()
                        .spec(usersSuccessfulCreationResponseSpec)
                        .extract().as(UserCreationResponseModel.class));

        step("Проверка содержимого ответа", () -> {
            assertThat(response.getName(), is(nullValue()));
            assertThat(response.getJob(), is(nullValue()));
            assertThat(response.getId(), matchesRegex("\\d+"));
            assertThat(response.getCreatedAt(), matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
        });
    }

    @Test
    @DisplayName("POST. Создание нового пользователя с пустым job")
    @Tag("Негативный")
    void negativeUserCreationNoJobTest() {

        UsersBodyModel bodyData = new UsersBodyModel();
        bodyData.setName("neo");

        UserCreationResponseModel response = step("Отправка запроса", ()->
                given(basicUsersRequestSpec)
                        .body(bodyData)

                        .when()
                        .post("/users")

                        .then()
                        .spec(usersSuccessfulCreationResponseSpec)
                        .extract().as(UserCreationResponseModel.class));

        step("Проверка содержимого ответа", () -> {
            assertThat(response.getName(), is(bodyData.getName()));
            assertThat(response.getJob(), is(nullValue()));
            assertThat(response.getId(), matchesRegex("\\d+"));
            assertThat(response.getCreatedAt(), matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
        });
    }

    @Test
    @DisplayName("PUT. Обновление данных пользователя")
    @Tag("Позитивный")
    void successfulUserFullUpdateTest() {

        UsersBodyModel bodyData = new UsersBodyModel();
        bodyData.setName("neo");
        bodyData.setJob("not the chosen one");

        UserUpdateResponseModel response = step("Отправка запроса", ()->
                given(basicUsersRequestSpec)
                        .body(bodyData)

                        .when()
                        .put("/users/2")

                        .then()
                        .spec(usersSuccessfulUpdatingResponseSpec)
                        .extract().as(UserUpdateResponseModel.class));

        step("Проверка содержимого ответа", () -> {
            assertThat(response.getName(), is(notNullValue()));
            assertThat(response.getName(), is(bodyData.getName()));
            assertThat(response.getJob(), is(notNullValue()));
            assertThat(response.getJob(), is(bodyData.getJob()));
            assertThat(response.getUpdatedAt(), matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
        });
    }

    @Test
    @DisplayName("PUT. Обновление данных пользователя - только имя")
    @Tag("Позитивный")
    void successfulUserPutUpdateNameOnlyTest() {

        UsersBodyModel bodyData = new UsersBodyModel();
        bodyData.setName("neo");

        UserUpdateResponseModel response = step("Отправка запроса", ()->
                given(basicUsersRequestSpec)
                        .body(bodyData)

                        .when()
                        .put("/users/2")

                        .then()
                        .spec(usersSuccessfulUpdatingResponseSpec)
                        .extract().as(UserUpdateResponseModel.class));

        step("Проверка содержимого ответа", () -> {
            assertThat(response.getName(), is(notNullValue()));
            assertThat(response.getName(), is(bodyData.getName()));
            assertThat(response.getJob(), is(nullValue()));
            assertThat(response.getUpdatedAt(), matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
        });
    }

    @Test
    @DisplayName("PATCH. Обновление данных пользователя")
    @Tag("Позитивный")
    void successfulUserPatchUpdateTest() {

        UsersBodyModel bodyData = new UsersBodyModel();
        bodyData.setName("neo");
        bodyData.setJob("not the chosen one");

        UserUpdateResponseModel response = step("Отправка запроса", ()->
                given(basicUsersRequestSpec)
                        .body(bodyData)

                        .when()
                        .patch("/users/2")

                        .then()
                        .spec(usersSuccessfulUpdatingResponseSpec)
                        .extract().as(UserUpdateResponseModel.class));

        step("Проверка содержимого ответа", () -> {
            assertThat(response.getName(), is(notNullValue()));
            assertThat(response.getName(), is(bodyData.getName()));
            assertThat(response.getJob(), is(notNullValue()));
            assertThat(response.getJob(), is(bodyData.getJob()));
            assertThat(response.getUpdatedAt(), matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
        });
    }

    @Test
    @DisplayName("DELETE. Удаление пользователя")
    @Tag("Позитивный")
    void successfulUserDeleteTest() {

        UsersBodyModel bodyData = new UsersBodyModel();
        bodyData.setName("neo");
        bodyData.setJob("the chosen one");

        Response response = step("Отправка запроса", () ->
                given(basicUsersRequestSpec)
                        .body(bodyData)

                        .when()
                        .delete("/users/2")

                        .then()
                        .spec(usersSuccessfulDeletingResponseSpec)
                        .extract().response());

        step("Проверка содержимого ответа", () -> {
            assertThat(response.asString(), equalTo(""));
        });

    }

}