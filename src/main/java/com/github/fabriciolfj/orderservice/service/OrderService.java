package com.github.fabriciolfj.orderservice.service;

import com.github.fabriciolfj.orderservice.client.BookClient;
import com.github.fabriciolfj.orderservice.client.BookResponse;
import com.github.fabriciolfj.orderservice.domain.Order;
import com.github.fabriciolfj.orderservice.domain.OrderStatus;
import com.github.fabriciolfj.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookClient bookClient;

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> getOrder(final Long id) {
        return orderRepository.findById(id);
    }

    public Mono<Order> submitOrder(final String isbn, final int quantity) {
        return bookClient.getBookByIsbn(isbn)
                .flatMap(bookResponse -> Mono.just(buildAcceptedOrder(bookResponse, quantity)))
                .defaultIfEmpty(buildRejectedOrder(isbn, quantity))
                .onErrorReturn(buildRejectedOrder(isbn, quantity))
                .flatMap(orderRepository::save);
    }

    private Order buildAcceptedOrder(final BookResponse book, final int quantity) {
        return new Order(book.getIsbn(), book.getTitle() + " - " + book.getAuthor(), book.getPrice(), quantity, OrderStatus.ACCEPTED);
    }

    private Order buildRejectedOrder(final String isbn, final int quantity) {
        return new Order(isbn, quantity, OrderStatus.REJECTED);
    }
}
