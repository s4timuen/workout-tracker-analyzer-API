package com.s4timuen.springsecurityclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "/api/v1")
public class PlaceholderController {

    @Autowired
    WebClient webClient;

    /**
     * API endpoints for placeholder.
     */
    @GetMapping("/placeholder")
    public String[] placeholder(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client
    ) {
        return this.webClient
                .get()
                .uri("http://127.0.0.1:8090/api/v1/placeholder")
                .attributes(oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToMono(String[].class)
                .block();
    }
}
