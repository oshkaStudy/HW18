package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import pages.ProfilePage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static tests.TestData.*;
import static tests.TestData.TOKEN;

public class UiSteps {

    @Step("UI. Добавление токена к сессии")
    public UiSteps setTokenToSession() {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", USERID));
        getWebDriver().manage().addCookie(new Cookie("expires", EXPIRES));
        getWebDriver().manage().addCookie(new Cookie("token", TOKEN));
        return this;
    }

    @Step("UI. Проверяем отсутствие книги в таблице")
    public void checkBookWasDeleted() {
        open("/profile");
        ProfilePage profilePage = new ProfilePage();
        profilePage.checkGridHasNotBook(BOOK);
    }
}
