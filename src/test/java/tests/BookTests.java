package tests;

import helpers.WithLogin;
import org.openqa.selenium.Cookie;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static tests.TestData.*;

public class BookTests extends TestBase {

    @Test
    @DisplayName("Удаление добавленной книги")
    void debugTest () {

        ApiSteps apiStep = new ApiSteps();
        UISteps uiStep = new UISteps();

        apiStep.authorize();
        apiStep.deleteAllBooks();
        apiStep.addBook();
        apiStep.deleteOneBook();

    }

    @Test
    @DisplayName("Удаление добавленной книги с кастомной аннотацией")
    @WithLogin
    void debugTestWi () {

        ApiSteps apiStep = new ApiSteps();
        UISteps uiStep = new UISteps();

        apiStep.deleteAllBooks();
        apiStep.addBook();
        uiStep.setTokenToSession();
        uiStep.checkBookWasAdded();
        apiStep.deleteOneBook();
        uiStep.checkBookWasDeleted();

    }

    @Test
    void addBookToCollection_withDelete1Book_Test() {

        ApiSteps apiStep = new ApiSteps();

        apiStep.authorize();

        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                USERID, ISBN);

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + TOKEN)
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", USERID));
        getWebDriver().manage().addCookie(new Cookie("expires", EXPIRES));
        getWebDriver().manage().addCookie(new Cookie("token", TOKEN));

        open("/profile");
        $(".ReactTable").shouldHave(text("Speaking JavaScript"));
    }

}