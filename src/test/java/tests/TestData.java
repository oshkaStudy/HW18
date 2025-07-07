package tests;

public class TestData {

    public static String
            LOGIN = System.getProperty("demoqaLogin"),
            PASSWORD = System.getProperty("demoqaPassword"),
            ISBN = "9781449365035",
            BOOK = "Speaking JavaScript",
            BOOK_ENDPOINT = "/BookStore/v1/Book",
            BOOKS_ENDPOINT = "/BookStore/v1/Books",
            LOGIN_ENDPOINT = "/Account/v1/Login";

    //Переменные объявляются через запрос авторизации
    public static String
            TOKEN,
            USERID,
            EXPIRES;

}
