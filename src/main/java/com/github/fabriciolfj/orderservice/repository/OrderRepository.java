package com.github.fabriciolfj.orderservice.repository;

import com.github.fabriciolfj.orderservice.domain.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
}
