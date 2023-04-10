package com.techsophy.proxyengine.service.implementation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProxyServiceTest {

    @Test
    void template() {
        ProxyService service = new ProxyService();
        service.template("anyName");

    }
}