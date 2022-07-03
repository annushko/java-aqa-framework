package com.github.annushko.core.http.apache;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.annushko.core.jackson.JacksonHolder;
import com.networknt.schema.JsonMetaSchema;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Objects;

public class JsonSchemaReader {

    private static final JsonSchemaFactory FACTORY_V7;

    static {
        FACTORY_V7 = new JsonSchemaFactory.Builder()
                             .defaultMetaSchemaURI(JsonMetaSchema.getV7().getUri())
                             .addMetaSchema(JsonMetaSchema.getV7())
                             .build();
    }

    public JsonSchema readFile(String file) {
        InputStream inputStream = Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(file),
                                                         "Schema file not found: " + file);
        try {
            JsonNode node = JacksonHolder.DEFAULT.readTree(inputStream);
            return FACTORY_V7.getSchema(node);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
