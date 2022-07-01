package in.reqres.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UsersCrudTest extends AbstractReqresInTest {

    private String id;

    @Test
    @Order(1)
    public void postUser() {
        Response response = given().contentType(ContentType.JSON)
                                   .body("{\n" +
                                         "    \"name\": \"morpheus\",\n" +
                                         "    \"job\": \"leader\"\n" +
                                         "}")
                                   .post("/api/users");
        response.then()
                .assertThat()
                .statusCode(201)
                .body("name", equalTo("morpheus"),
                      "job", equalTo("leader"),
                      "id", notNullValue());
        this.id = response.path("id");
    }

    @Test
    @Order(2)
    public void putUser() {
        Response response = given().contentType(ContentType.JSON)
                                   .body("{\n" +
                                         "    \"name\": \"morpheus\",\n" +
                                         "    \"job\": \"zion resident\"\n" +
                                         "}")
                                   .put("/api/users/{id}", id);
        response.then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("morpheus"),
                      "job", equalTo("zion resident"));
    }

    @Test
    @Order(3)
    public void patchUser() {
        Response response = given().contentType(ContentType.JSON)
                                   .body("{\n" +
                                         "    \"job\": \"zion\"\n" +
                                         "}")
                                   .put("/api/users/{id}", id);
        response.then()
                .assertThat()
                .statusCode(200)
                .body("job", equalTo("zion"));
    }

    @Test
    @Order(4)
    public void deleteUser() {
        Response response = given().contentType(ContentType.JSON)
                                   .when()
                                   .delete("/api/users/{id}", id);
        response.then()
                .assertThat()
                .statusCode(204)
                .body(blankOrNullString());
    }

}
