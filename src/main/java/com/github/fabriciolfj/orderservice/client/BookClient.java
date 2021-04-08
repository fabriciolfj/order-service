package com.github.fabriciolfj.orderservice.client;

import com.github.fabriciolfj.orderservice.config.BookClientProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class BookClient {

    private final WebClient webClient;

    public BookClient(final BookClientProperties properties, final WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                            .baseUrl(properties.getCatalogServiceUrl().toString())
                            .build();
    }

    public Mono<BookResponse> getBookByIsbn(final String isbn) {
        return webClient.get()
                .uri(isbn)
                .retrieve()
                .bodyToMono(BookResponse.class)
                .timeout(Duration.ofSeconds(1), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.empty())
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)));
    }
}
