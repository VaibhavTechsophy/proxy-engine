package com.techsophy.proxyengine.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceOperationDefinition {

    private HttpMethod method;
    private String targetURI;

    private String payload;
}
