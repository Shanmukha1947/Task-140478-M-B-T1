package com.example.webscrapermiddleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class WebScraperController {

    private final WebClient webClient;

    @Autowired
    public WebScraperController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://example.com").build();
    }

    @GetMapping("/scrape")
    public Mono<String> scrapeWebsite() {
        return webClient.get()
                .uri("/nonexistent")
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("Fallback response due to error: " + e.getMessage()));
    }
}
