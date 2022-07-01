package in.reqres.apache.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.annushko.core.http.apache.HttpExecutor;
import com.github.annushko.core.http.apache.JsonSchemaReader;
import com.github.annushko.core.jackson.JacksonMapper;
import com.github.annushko.junit.extension.HttpExecutorParameterResolver;
import com.github.annushko.junit.extension.JacksonMapperParameterResolver;
import com.networknt.schema.ValidationMessage;
import in.reqres.AbstractReqresInTest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

@ExtendWith({JacksonMapperParameterResolver.class, HttpExecutorParameterResolver.class})
abstract class AbstractApacheApiTest extends AbstractReqresInTest {

    protected final HttpExecutor http;
    protected final JacksonMapper mapper;

    public AbstractApacheApiTest(HttpExecutor http, JacksonMapper mapper) {
        this.http = http;
        this.mapper = mapper;
    }

    protected void validateSchema(SoftAssertions softAssertions, JsonNode node, String file) {
        Set<ValidationMessage> errors = new JsonSchemaReader().readFile(file).validate(node);
        softAssertions.assertThat(errors)
                      .withFailMessage(() -> {
                          StringBuilder stringBuilder = new StringBuilder().append("Schema file: ")
                                                                           .append(file)
                                                                           .append("\n");
                          errors.forEach(validationMessage -> stringBuilder.append(validationMessage)
                                                                           .append("\n"));
                          return stringBuilder.toString();
                      })
                      .as("Some errors were found!")
                      .isEmpty();
    }

}
