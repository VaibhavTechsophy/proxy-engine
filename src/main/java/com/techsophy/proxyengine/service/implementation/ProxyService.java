package com.techsophy.proxyengine.service.implementation;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.techsophy.proxyengine.data.ProxyDefinition;
import com.techsophy.proxyengine.data.ProxyRequest;
import com.techsophy.proxyengine.data.ResourceOperationDefinition;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
public class ProxyService implements com.techsophy.proxyengine.service.ProxyService {
    @Autowired
    private WebClient serviceClient;


    public Mono<byte[]> callProxy(ProxyDefinition proxy, ProxyRequest request) {


        String templateName = proxy.getResource() + "_" + proxy.getOperation() + ".ftlh";
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setLogTemplateExceptions(true);
        configuration.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "templates");
        String definitionString = "";
        try {
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
            JsonSchema schema = factory.getSchema(proxy.getValidation().getQuery());
            Set<ValidationMessage> errors = schema.validate(request.getQueryParams());
            if (errors != null && !errors.isEmpty()) {
                throw new RuntimeException("Bad request");
            }

            Template template = configuration.getTemplate(templateName);
            definitionString = FreeMarkerTemplateUtils.processTemplateIntoString(template, request);
            ObjectMapper mapper = new ObjectMapper();
            ResourceOperationDefinition resource = mapper.readValue(definitionString, ResourceOperationDefinition.class);
            List<Object> specs = null;

            specs = JsonUtils.jsonToList(
                    mapper.writeValueAsString(proxy.getJsonTransformer().getSuccess()));

            Chainr chainr = Chainr.fromSpec(specs);

            return serviceClient
                    .method(resource.getMethod())
                    .uri(resource.getTargetURI())
                    .bodyValue(resource.getPayload()
                    ).exchangeToMono((clientResponse -> {
                        if (clientResponse.statusCode().isError()) {
                            return clientResponse.bodyToMono(byte[].class);
                        }
                        return clientResponse.headers().contentType().filter(
                                        mediaType -> mediaType.equals(MediaType.APPLICATION_JSON))
                                .map(mediaType -> {
                                    return Mono.fromSupplier(() -> {
                                        return clientResponse.bodyToMono(String.class)
                                                .map(response -> {
                                                    if (chainr != null) {

                                                        Object o = chainr.transform(JsonUtils.jsonToObject(response));
                                                        response = JsonUtils.toJsonString(o);
                                                    }
                                                    return response.getBytes();
                                                });
                                    });
                                }).get().block();
                    }));
        } catch (
                JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (
                Exception e) {
            throw new RuntimeException(e);
        }
//        String downloadTemplate;
//        downloadTemplate = serviceClient.get().uri("/template-modeler/v1/templates/download/"+proxy.getTemplateId())webclientRequest(webClient,
//                gatewayUri + TEMPLATE_MODELER_V1_TEMPLATES_DOWNLOAD + templateData.getTemplateId(), GET, null);
//        checkError(downloadTemplate);
//        String freeMarkerString=freemarkerService.processTemplate(downloadTemplate,templateData.getTemplateData());
//        InputStream targetStream = IOUtils.toInputStream(freeMarkerString);
//        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.setHeader(CONTENT_DISPOSITION, ATTACHMENT_FILE_NAME );
//        IOUtils.copy(targetStream, response.getOutputStream());
//        logger.info(PROCESSING_TEMPLATE_WITH_PARAMS, targetStream);
//        response.flushBuffer();


    }


}
