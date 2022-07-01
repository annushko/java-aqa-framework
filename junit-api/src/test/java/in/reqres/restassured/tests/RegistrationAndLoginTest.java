package in.reqres.restassured.tests;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RegistrationAndLoginTest extends AbstractRestAssured {

    @Test
    public void successfulRegistrationUser() {
        Response response = given().contentType(ContentType.JSON)
                                   .body("{" +
                                         "    \"email\": \"eve.holt@reqres.in\"," +
                                         "    \"password\": \"pistol\"" +
                                         "}")
                                   .post("/api/register");
        response.then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue(),
                      "token", notNullValue());
    }

    @Test
    public void unsuccessfulRegistrationUser() {
        Response response = given().contentType(ContentType.JSON)
                                   .body("{" +
                                         "    \"email\": \"sydney@fife\"," +
                                         "    \"password\": \"pistol\"" +
                                         "}")
                                   .post("/api/register");
        response.then()
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("Note: Only defined users succeed registration"));
    }

    @Test
    public void successfulLogin() {
        Response response = given().contentType(ContentType.JSON)
                                   .body("{" +
                                         "    \"email\": \"eve.holt@reqres.in\"," +
                                         "    \"password\": \"cityslicka\"" +
                                         "}")
                                   .post("/api/login");
        response.then()
                .assertThat()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    public void unsuccessfulLogin() {
        Response response = given().contentType(ContentType.JSON)
                                   .body("{" +
                                         "    \"email\": \"peter@klaven\"," +
                                         "}")
                                   .post("/api/login");

        response.then().assertThat()
                .statusCode(400);

        XmlPath xmlPath = response.then().extract().htmlPath();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(xmlPath.getString("html.head.title"))
                          .as("Html title is wrong!")
                          .isEqualTo("Error");
            softAssertions.assertThat(xmlPath.getString("html.body.pre"))
                          .as("Html pre is wrong!")
                          .isEqualTo("Bad Request");
        });
    }
}
