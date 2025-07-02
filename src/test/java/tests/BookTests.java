package tests;

import helpers.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BookTests extends TestBase {

    @Test
    @DisplayName("Удаление добавленной книги")
    void debugTest () {

        ApiSteps apiSteps = new ApiSteps();
        UISteps uiSteps = new UISteps();

        apiSteps
                .authorize()
                .deleteAllBooks()
                .addBook()
                .deleteOneBook();

        uiSteps
                .setTokenToSession()
                .checkBookWasDeleted();

    }

    @Test
    @DisplayName("Удаление добавленной книги с кастомной аннотацией")
    @WithLogin
    void debugTestWi () {

        ApiSteps apiSteps = new ApiSteps();
        UISteps uiSteps = new UISteps();

        apiSteps
                .deleteAllBooks()
                .addBook()
                .deleteOneBook();

        uiSteps
                .setTokenToSession()
                .checkBookWasDeleted();

    }

}