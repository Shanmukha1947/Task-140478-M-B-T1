package com.example.webscrapermiddleware;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@Component
public class ErrorHandlingMiddleware {

    public Mono<ClientResponse> handleErrors(Mono<ClientResponse> responseMono) {
        return responseMono
                .onErrorResume(e -> {
                    if (e instanceof WebClientResponseException) {
                        WebClientResponseException responseException = (WebClientResponseException) e;
                        // Log the error and handle it
                        System.err.println("Error occurred: " + responseException.getMessage());
                        // Optionally, you can retry the request here or return a fallback response
                        return Mono.just(ClientResponse.create(responseException.getStatusCode()).build());
                    }
                    // Handle other types of errors
                    return Mono.error(new ScrapingException("Unexpected error: " + e.getMessage()));
                });
    }
}
