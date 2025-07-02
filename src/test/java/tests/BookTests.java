package tests;

import org.openqa.selenium.Cookie;
import io.restassured.response.Response;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specifications.UserSpec.*;

public class BookTests extends TestBase {

    @Test
    @DisplayName("Удаление книги из профиля")
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
    void addBookToCollection_withDelete1Book_Test() {

        String login = "oshkaStudy";
        String password = "123oshkaStudy_!";

        String authData = "{\"userName\":\"" + login + "\",\"password\":\"" + password + "\"}";

        Response authResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response();

        String isbn = "9781449365035";
        String deleteBookData = format("{\"userId\":\"%s\",\"isbn\":\"%s\"}",
                authResponse.path("userId") , isbn);

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .body(deleteBookData)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);

        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authResponse.path("userId") , isbn);

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));

        open("/profile");
        $(".ReactTable").shouldHave(text("Speaking JavaScript"));
    }

}