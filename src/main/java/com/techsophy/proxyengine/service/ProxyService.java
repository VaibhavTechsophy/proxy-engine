package com.techsophy.proxyengine.service;


import com.techsophy.proxyengine.data.ProxyDefinition;
import com.techsophy.proxyengine.data.ProxyRequest;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface ProxyService {
    Mono<byte[]> callProxy(ProxyDefinition proxy,   ProxyRequest request);//String resource, String operation,

    String template(String templateName);

}
