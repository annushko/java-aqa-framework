package in.reqres.apache.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.annusko93.core.http.apachehttp.HttpExecutor;
import com.github.annusko93.core.http.apachehttp.UrlParams;
import com.github.annusko93.core.jackson.JacksonMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class GetUsersTest extends AbstractApacheApiTest {

    public GetUsersTest(HttpExecutor http, JacksonMapper mapper) {
        super(http, mapper);
    }

    @Test
    @DisplayName("Get list of Users")
    public void getListOfUsers() {
        List<NameValuePair> params = UrlParams.from("page", "2");
        CloseableHttpResponse response = http.get(BASE_URL + "/api/users", params);

        JsonNode node = mapper.extractJson(response);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getStatusLine().getStatusCode())
                          .as("Incorrect status code!")
                          .isEqualTo(200);
            validateSchema(softAssertions, node, "schema/getListOfUsers.json");
        });
    }

    @ParameterizedTest
    @CsvSource({"2,janet.weaver@reqres.in,Janet,Weaver,https://reqres.in/img/faces/2-image.jpg",
                "3,emma.wong@reqres.in,Emma,Wong,https://reqres.in/img/faces/3-image.jpg",
                "4,eve.holt@reqres.in,Eve,Holt,https://reqres.in/img/faces/4-image.jpg"})
    @DisplayName("Get User by Id")
    public void getUserById(Integer id, String email, String firstName, String lastName, String avatar) {
        CloseableHttpResponse response = http.get(BASE_URL + "/api/users/" + id);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getStatusLine().getStatusCode())
                          .as("Incorrect status code!")
                          .isEqualTo(200);

            JsonNode node = mapper.extractJson(response);

            softAssertions.assertThat(node.get("data").get("id").asInt())
                          .as("Incorrect id!")
                          .isEqualTo(id);
            softAssertions.assertThat(node.get("data").get("email").asText())
                          .as("Incorrect email!")
                          .isEqualTo(email);
            softAssertions.assertThat(node.get("data").get("first_name").asText())
                          .as("Incorrect first_name!")
                          .isEqualTo(firstName);
            softAssertions.assertThat(node.get("data").get("last_name").asText())
                          .as("Incorrect last_name!")
                          .isEqualTo(lastName);
            softAssertions.assertThat(node.get("data").get("avatar").asText())
                          .as("Incorrect avatar!")
                          .isEqualTo(avatar);
        });
    }

    @Test
    @DisplayName("Get User by incorrect Id")
    public void getUserByIncorrectId() {
        int incorrectId = 9999;
        CloseableHttpResponse response = http.get(BASE_URL + "/api/users/" + incorrectId);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getStatusLine().getStatusCode())
                          .as("Incorrect status code!")
                          .isEqualTo(404);
        });
    }

}
