package tests;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import pages.ProfilePage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static tests.TestData.*;
import static tests.TestData.TOKEN;

public class UISteps extends TestBase {

    @Step("Проверяем наличие книги в таблице")
    public void checkBookWasAdded() {
        open("/profile");
        ProfilePage profilePage = new ProfilePage();
        profilePage.checkGridForBook(BOOK);
    }

    @Step("Добавление токена к сессии")
    public void setTokenToSession() {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", USERID));
        getWebDriver().manage().addCookie(new Cookie("expires", EXPIRES));
        getWebDriver().manage().addCookie(new Cookie("token", TOKEN));
    }

    @Step("Проверяем отсутствие книги в таблице")
    public void checkBookWasDeleted() {
        open("/profile");
        ProfilePage profilePage = new ProfilePage();
        profilePage.checkGridForBook(BOOK);
    }
}
