package in.reqres.restassured.tests;

import in.reqres.AbstractReqresInTest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

abstract class AbstractRestAssured extends AbstractReqresInTest {

    protected RequestSpecification given() {
        return RestAssured.given()
                          .baseUri(BASE_URL);
    }

}
