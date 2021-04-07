package com.github.fabriciolfj.orderservice.client;

import com.github.fabriciolfj.orderservice.config.BookClientProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                .bodyToMono(BookResponse.class);
    }
}
