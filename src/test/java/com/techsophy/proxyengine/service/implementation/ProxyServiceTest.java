package com.techsophy.proxyengine.service.implementation;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProxyServiceTest {

    @Test
    void template() throws IOException {
        ProxyService service = new ProxyService();
        System.out.println(service.template("anyName").toPrettyString());

    }
}