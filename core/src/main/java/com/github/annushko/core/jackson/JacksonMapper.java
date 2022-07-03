package com.github.annushko.core.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.annushko.core.http.apache.JsonBody;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ByteArrayEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class JacksonMapper extends ObjectMapper {

    public JacksonMapper() {
        super();
    }

    public JacksonMapper(JacksonMapper src) {
        super(src);
    }

    public JsonBody prepareBody(String body) {
        try {
            return new JsonBody(this.readTree(body));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonNode extractJson(CloseableHttpResponse response) {
        try {
            InputStream inputStream = response.getEntity().getContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            inputStream.transferTo(baos);
            baos.flush();
            response.setEntity(new ByteArrayEntity(baos.toByteArray()));
            return this.readTree(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JacksonMapper copy() {
        return new JacksonMapper(this);
    }
}
