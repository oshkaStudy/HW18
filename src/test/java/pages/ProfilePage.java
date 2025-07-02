package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {

    public SelenideElement grid = $(".ReactTable");

    public void checkGridForBook(String bookName) {
        grid.shouldHave(text(bookName));
    }

    public void checkGridIsEmpty(String bookName) {
        grid.shouldNotHave(text(bookName));
    }

}
