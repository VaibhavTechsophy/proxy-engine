package com.techsophy.proxyengine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsophy.proxyengine.data.ProxyDefinition;
import com.techsophy.proxyengine.data.ProxyRequest;
import com.techsophy.proxyengine.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ProxyEngineApplication implements CommandLineRunner {

	@Autowired
	ApplicationContext context;
	public static void main(String[] args) {
		SpringApplication.run(ProxyEngineApplication.class, args);
	}

	@Bean
	WebClient serviceClient(ReactiveClientRegistrationRepository clientRegistrations) {
		ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
				new ServerOAuth2AuthorizedClientExchangeFilterFunction(
						clientRegistrations,
						new UnAuthenticatedServerOAuth2AuthorizedClientRepository());
		oauth.setDefaultClientRegistrationId("keycloak");
		return WebClient.builder()
				.baseUrl("https://api-gateway.techsophy.com/api")
				.filter(oauth)
				.build();
	}


	@Override
	public void run(String... args) throws Exception {

		Map<String,Object>  data = new HashMap<>();
		data.put("formId",1074611413485924352L);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode queryParams = mapper.convertValue(data, JsonNode.class);
		data.clear();
		data.put("preferred_username","vaibhav");
		ProxyRequest request = new ProxyRequest(null,queryParams,null);
		ProxyService service = context.getBean(ProxyService.class);

		ProxyDefinition proxy = mapper.readValue(
				getClass().getResource("/proxy.json"),
				ProxyDefinition.class);

		Mono<byte[]> response = service.callProxy(proxy,request);


		System.out.println(new String(response.block()));

//		System.out.flush();
//		Mono<byte[]> response = service.callProxy(definition);
//		System.out.println(new String(response.block()));
	}


}
