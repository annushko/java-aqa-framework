package in.reqres.restassured.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class GetUsersTest extends AbstractRestAssured {

    @Test
    @DisplayName("Get list of Users")
    public void getListOfUsers() {
        given().queryParam("page", "2")
               .get("/api/users")
               .then()
               .statusCode(200)
               .assertThat().body(matchesJsonSchemaInClasspath("schema/getListOfUsers.json"));
    }

    @ParameterizedTest
    @CsvSource({"2,janet.weaver@reqres.in,Janet,Weaver,https://reqres.in/img/faces/2-image.jpg",
                "3,emma.wong@reqres.in,Emma,Wong,https://reqres.in/img/faces/3-image.jpg",
                "4,eve.holt@reqres.in,Eve,Holt,https://reqres.in/img/faces/4-image.jpg"})
    @DisplayName("Get User by Id")
    public void getUserById(Integer id, String email, String firstName, String lastName, String avatar) {
        given().get("/api/users/{id}", id)
               .then()
               .statusCode(200)
               .body("data.id", equalTo(id),
                     "data.email", equalTo(email),
                     "data.first_name", equalTo(firstName),
                     "data.last_name", equalTo(lastName),
                     "data.avatar", equalTo(avatar));
    }

    @Test
    @DisplayName("Get User by incorrect Id")
    public void getUserByIncorrectId() {
        given().get("/api/users/{id}", 9999)
               .then()
               .statusCode(404);
    }

}
