package in.reqres.tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

abstract class AbstractReqresInTest {

    protected final String baseUri = "https://reqres.in";

    protected RequestSpecification given() {
        return RestAssured.given()
                   .baseUri(baseUri);
    }

}
