package specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class BasicSpec {

    public static final RequestSpecification baseRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .contentType(JSON);

    /**
     * Обобщенная спецификация респонса
     * @param expectedStatusCode ожидаемый статус ответа
     */

    public static ResponseSpecification responseSpecWithCode(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .log(STATUS)
                .log(BODY)
                .expectStatusCode(expectedStatusCode)
                .build();
    }
}
