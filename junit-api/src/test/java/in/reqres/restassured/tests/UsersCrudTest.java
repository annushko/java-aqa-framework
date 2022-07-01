package in.reqres.restassured.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UsersCrudTest extends AbstractRestAssured {

    private String id;
    private String name = "morpheus";

    @Test
    @Order(1)
    public void postUser() {
        String job = "leader";
        Response response = given().contentType(ContentType.JSON)
                                   .body("{" +
                                         "    \"name\": \"" + name + "\"," +
                                         "    \"job\": \"" + job + "\"" +
                                         "}")
                                   .post("/api/users");
        response.then()
                .assertThat()
                .statusCode(201)
                .body("name", equalTo(name),
                      "job", equalTo(job),
                      "id", notNullValue());
        this.id = response.path("id");
    }

    @Test
    @Order(2)
    public void putUser() {
        String job = "zion resident";
        Response response = given().contentType(ContentType.JSON)
                                   .body("{" +
                                         "    \"name\": \"" + name + "\"," +
                                         "    \"job\": \"" + job + "\"" +
                                         "}")
                                   .put("/api/users/{id}", id);
        response.then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo(name),
                      "job", equalTo(job));
    }

    @Test
    @Order(3)
    public void patchUser() {
        String job = "zion";
        Response response = given().contentType(ContentType.JSON)
                                   .body("{" +
                                         "    \"job\": \"" + job + "\"" +
                                         "}")
                                   .put("/api/users/{id}", id);
        response.then()
                .assertThat()
                .statusCode(200)
                .body("job", equalTo(job));
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
