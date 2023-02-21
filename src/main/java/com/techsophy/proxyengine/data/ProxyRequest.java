package com.techsophy.proxyengine.data;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProxyRequest {

    JsonNode jwt;
    JsonNode queryParams;

    JsonNode payload;



}
