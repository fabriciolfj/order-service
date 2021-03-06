package com.github.fabriciolfj.orderservice;

import com.github.fabriciolfj.orderservice.client.BookClient;
import com.github.fabriciolfj.orderservice.client.BookResponse;
import com.github.fabriciolfj.orderservice.config.BookClientProperties;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.io.IOException;

public class BookClientTests {

    private MockWebServer mockWebServer;
    private BookClient bookClient;

    @BeforeEach
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        BookClientProperties bookClientProperties = new BookClientProperties();
        bookClientProperties.setCatalogServiceUrl(mockWebServer.url("/").uri());
        this.bookClient = new BookClient(bookClientProperties, WebClient.builder());
    }

    @AfterEach
    void clean() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void whenBookExistsThenReturnBook() {
        String bookIsbn = "1234567890";

        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("{\"isbn\":\"" + bookIsbn + "\",\"title\":\"Book Title\", \"author\":\"Book Author\", \"publishingYear\":\"1973\", \"price\":\"9.90\"}");

        mockWebServer.enqueue(mockResponse);

        Mono<BookResponse> book = bookClient.getBookByIsbn(bookIsbn);

        StepVerifier.create(book)
                .expectNextMatches(b -> b.getIsbn().equals(bookIsbn))
                .verifyComplete();
    }
}
