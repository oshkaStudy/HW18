package tests;

import helpers.WithLogin;
import models.AddBooksResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.*;

public class BookTests extends TestBase {

    @Test
    @DisplayName("Удаление добавленной книги")
    void deleteBookTest () {

        ApiSteps apiSteps = new ApiSteps();
        UISteps uiSteps = new UISteps();

        apiSteps.authorize();

        apiSteps.deleteAllBooks();

        AddBooksResponseModel addBookResponse = apiSteps.addBook();

        apiSteps.assertBookAdded(addBookResponse);

        apiSteps.deleteOneBook();

        uiSteps
                .setTokenToSession()
                .checkBookWasDeleted();
    }

    @Test
    @DisplayName("Удаление добавленной книги с кастомной аннотацией")
    @WithLogin
    void deleteBookWithAnnotationTest () {

        ApiSteps apiSteps = new ApiSteps();
        UISteps uiSteps = new UISteps();

        apiSteps.deleteAllBooks();

        AddBooksResponseModel addBookResponse = apiSteps.addBook();

        apiSteps.assertBookAdded(addBookResponse);

        apiSteps.deleteOneBook();

        uiSteps
                .setTokenToSession()
                .checkBookWasDeleted();
    }

}