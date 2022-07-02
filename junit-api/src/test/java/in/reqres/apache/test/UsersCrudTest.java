package in.reqres.apache.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.annusko93.core.http.apachehttp.HttpExecutor;
import com.github.annusko93.core.http.apachehttp.JsonBody;
import com.github.annusko93.core.jackson.JacksonMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class UsersCrudTest extends AbstractApacheApiTest {

    private String id;
    private String name = "morpheus";

    public UsersCrudTest(HttpExecutor http, JacksonMapper mapper) {
        super(http, mapper);
    }

    @Test
    @Order(1)
    public void postUser() {
        String job = "leader";
        String example = "{" +
                         "    \"name\": \"" + name + "\"," +
                         "    \"job\": \"" + job + "\"" +
                         "}";
        JsonBody body = mapper.prepareBody(example);

        CloseableHttpResponse response = http.post(BASE_URL + "/api/users", body);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getStatusLine().getStatusCode())
                          .as("Incorrect status code!")
                          .isEqualTo(201);

            JsonNode node = mapper.extractJson(response);

            softAssertions.assertThat(node.get("name").asText())
                          .as("Incorrect name!")
                          .isEqualTo(name);
            softAssertions.assertThat(node.get("job").asText())
                          .as("Incorrect job!")
                          .isEqualTo(job);
            softAssertions.assertThat(node.get("id").asInt())
                          .as("Id must be not null!")
                          .isNotNull();

            this.id = node.get("id").asText();
        });
    }

    @Test
    @Order(2)
    public void putUser() {
        String job = "zion resident";
        String example = "{" +
                         "    \"name\": \"" + name + "\"," +
                         "    \"job\": \"" + job + "\"" +
                         "}";
        JsonBody body = mapper.prepareBody(example);

        CloseableHttpResponse response = http.put(BASE_URL + "/api/users/" + id, body);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getStatusLine().getStatusCode())
                          .as("Incorrect status code!")
                          .isEqualTo(200);

            JsonNode node = mapper.extractJson(response);

            softAssertions.assertThat(node.get("name").asText())
                          .as("Incorrect name!")
                          .isEqualTo(name);
            softAssertions.assertThat(node.get("job").asText())
                          .as("Incorrect job!")
                          .isEqualTo(job);
        });
    }

    @Test
    @Order(3)
    public void patchUser() {
        String job = "zion";
        String example = "{" +
                         "    \"job\": \"" + job + "\"" +
                         "}";
        JsonBody body = mapper.prepareBody(example);

        CloseableHttpResponse response = http.patch(BASE_URL + "/api/users/" + id, body);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getStatusLine().getStatusCode())
                          .as("Incorrect status code!")
                          .isEqualTo(200);

            JsonNode node = mapper.extractJson(response);

            softAssertions.assertThat(node.get("job").asText())
                          .as("Incorrect job!")
                          .isEqualTo(job);
        });
    }

    @Test
    @Order(4)
    public void deleteUser() {
        CloseableHttpResponse response = http.delete(BASE_URL + "/api/users/" + id);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getStatusLine().getStatusCode())
                          .as("Incorrect status code!")
                          .isEqualTo(204);
        });
    }

}
