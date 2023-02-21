package com.techsophy.proxyengine.data;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProxyDefinition {
    private String resource;
    private String operation;
    private Validation validation;

    private JsonTransformation jsonTransformer;

    @Data
    @NoArgsConstructor
    public static class Validation {
        private JsonNode query;

        private JsonNode payload;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JsonTransformation{
        private JsonNode success;
    }


}
