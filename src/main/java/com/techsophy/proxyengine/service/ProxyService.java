package com.techsophy.proxyengine.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.techsophy.proxyengine.data.ProxyDefinition;
import com.techsophy.proxyengine.data.ProxyRequest;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface ProxyService {
    Mono<byte[]> callProxy(ProxyDefinition proxy,   ProxyRequest request);//String resource, String operation,

    JsonNode template(String templateName) throws IOException;

}
