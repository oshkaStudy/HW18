package steps;

import io.qameta.allure.Step;
import models.GenerateTokenBodyModel;
import models.GenerateTokenResponseModel;
import models.LoginBodyModel;
import models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static specifications.BasicSpec.baseRequestSpec;
import static specifications.BasicSpec.responseSpecWithCode;
import static tests.TestData.*;
import static helpers.DateUtil.isTokenExpired;

public class ApiAccountSteps {

    @Step("API. Авторизация и получение токена")
    public void authorize() {

        LoginBodyModel bodyData = new LoginBodyModel(LOGIN,PASSWORD);

        LoginResponseModel response =
                given(baseRequestSpec)
                        .body(bodyData)
                        .when()
                        .post(LOGIN_ENDPOINT)
                        .then()
                        .spec(responseSpecWithCode(200))
                        .extract().as(LoginResponseModel.class);

        TOKEN = response.getToken();
        USERID = response.getUserId();
        EXPIRES = response.getExpires();
        }

    @Step("API. Проверка и перегенерация токена")
    public void checkAndUpdateToken() {

        if (isTokenExpired(EXPIRES))
        {System.out.println("\nТокен активный, перегенерация не требуется\n");}
        else {

            GenerateTokenBodyModel bodyData = new GenerateTokenBodyModel(LOGIN,PASSWORD);

            GenerateTokenResponseModel response =
                    given(baseRequestSpec)
                            .body(bodyData)
                            .when()
                            .post(GENERATE_TOKEN_ENDPOINT)
                            .then()
                            .spec(responseSpecWithCode(200))
                            .extract().as(GenerateTokenResponseModel.class);

            TOKEN = response.getToken();
            EXPIRES = response.getExpires();
        }

    }

}
