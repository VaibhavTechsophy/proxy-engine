package com.techsophy.proxyengine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import reactor.core.publisher.Mono;

@Configuration
//@EnableWebSecurity
public class OAuth2ClientSecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .oauth2Client(oauth2 -> oauth2
//                        .clientRegistrationRepository(this.clientRegistrationRepository())
//                        .authorizedClientRepository(this.authorizedClientRepository())
//                        .authorizedClientService(this.authorizedClientService())
//                        .authorizationCodeGrant(codeGrant -> codeGrant
//                                .authorizationRequestRepository(this.authorizationRequestRepository())
//                                .authorizationRequestResolver(this.authorizationRequestResolver())
//                                .accessTokenResponseClient(this.accessTokenResponseClient())
//                        )
//                );
//        return http.build();
//    }
//
//    @Bean
//    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
//            ReactiveClientRegistrationRepository clientRegistrationRepository,
//            OAuth2AuthorizedClientRepository authorizedClientRepository) {
//
//        ReactiveOAuth2AuthorizedClientManager authorizedClientManager =
//                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository,
//                        new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrationRepository));
//
//
////        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//
//        return authorizedClientManager;
//    }
}