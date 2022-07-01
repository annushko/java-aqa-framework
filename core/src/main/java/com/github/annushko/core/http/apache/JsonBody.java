package com.github.annushko.core.http.apache;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonBody {

    private final JsonNode jsonNode;

    public JsonBody(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    public byte[] asBytes() {
        return asString().getBytes();
    }

    public String asString() {
        return jsonNode.toString();
    }
}
