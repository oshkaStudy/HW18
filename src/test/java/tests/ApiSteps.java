package tests;

import io.qameta.allure.Step;
import models.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static specifications.BasicSpec.*;
import static tests.TestData.*;

public class ApiSteps extends TestBase {

    @Step("API. Авторизация и получение токена")
    public ApiSteps authorize() {

        LoginBodyModel bodyData = new LoginBodyModel(LOGIN,PASSWORD);

        LoginResponseModel response =
                given(baseRequestSpec)
                        .body(bodyData)
                        .when()
                        .post(LOGIN_ENDPOINT)
                        .then()
                        .spec(responseSpecWithCode(200))
                        .extract().as(LoginResponseModel.class);

        TestData.TOKEN = response.getToken();
        TestData.USERID = response.getUserId();
        TestData.EXPIRES = response.getExpires();

        return this;
    }

    @Step("API. Удаление всех книг")
    public ApiSteps deleteAllBooks() {

        given(baseRequestSpec)
                .queryParams("UserId", USERID)
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .delete(BOOKS_ENDPOINT)
                .then()
                .spec(responseSpecWithCode(204))
                .extract().response();

        return this;
    }

    @Step("API. Добавление книги в профиль")
    public ApiSteps addBook() {

        AddBooksBodyModel bodyData = new AddBooksBodyModel(USERID, List.of(new IsbnModel(ISBN)));

        AddBooksResponseModel response =
                given(baseRequestSpec)
                    .header("Authorization", "Bearer " + TOKEN)
                    .body(bodyData)
                    .when()
                    .post(BOOKS_ENDPOINT)
                    .then()
                    .spec(responseSpecWithCode(201))
                    .extract().as(AddBooksResponseModel.class);

        assertThat(response.getBooks(), is(notNullValue()));
        assertThat(response.getBooks().size(), is(1));
        assertThat(response.getBooks().get(0).getIsbn(), is(ISBN));

        return this;
    }

    @Step("API. Удаление одной книги")
    public void deleteOneBook() {

        DeleteOneBookBodyModel bodyData = new DeleteOneBookBodyModel(ISBN,USERID);

        given(baseRequestSpec)
                .body(bodyData)
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .delete(BOOK_ENDPOINT)
                .then()
                .spec(responseSpecWithCode(204))
                .extract().response();
    }



}
