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

        ApiAccountSteps apiAccountSteps = new ApiAccountSteps();
        ApiBookStoreSteps apiBooksSteps = new ApiBookStoreSteps();
        UiSteps uiSteps = new UiSteps();

        apiAccountSteps.authorize();

        apiAccountSteps.checkAndUpdateToken();

        apiBooksSteps.deleteAllBooks();

        AddBooksResponseModel addBookResponse = apiBooksSteps.addBook();

        apiBooksSteps.assertBookAdded(addBookResponse);

        apiBooksSteps.deleteOneBook();

        uiSteps
                .setTokenToSession()
                .checkBookWasDeleted();
    }

    @Test
    @DisplayName("Удаление добавленной книги с кастомной аннотацией")
    @WithLogin
    void deleteBookWithAnnotationTest () {

        ApiBookStoreSteps apiBooksSteps = new ApiBookStoreSteps();
        UiSteps uiSteps = new UiSteps();

        apiBooksSteps.deleteAllBooks();

        AddBooksResponseModel addBookResponse = apiBooksSteps.addBook();

        apiBooksSteps.assertBookAdded(addBookResponse);

        apiBooksSteps.deleteOneBook();

        uiSteps
                .setTokenToSession()
                .checkBookWasDeleted();
    }

}