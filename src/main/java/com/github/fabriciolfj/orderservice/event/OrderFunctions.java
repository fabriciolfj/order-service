package com.github.fabriciolfj.orderservice.event;

import com.github.fabriciolfj.orderservice.domain.Order;
import com.github.fabriciolfj.orderservice.domain.OrderStatus;
import com.github.fabriciolfj.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class OrderFunctions {

    @Bean
    public Consumer<OrderDispatchedMessage> dispatchOrder(OrderService orderService) {
        return dispatchedMessageConsumer -> {
            log.info("The order with id {} has been dispatched",dispatchedMessageConsumer.getOrderId());
            orderService.updateOrderStatus(dispatchedMessageConsumer.getOrderId(), OrderStatus.DISPATCHED);
        };
    }

    @Bean
    @Qualifier("acceptSend")
    public Consumer<Order> publishOrderAcceptedEvent(StreamBridge streamBridge) {
        return order -> {
            if (!order.getStatus().equals(OrderStatus.ACCEPTED)) {
                return;
            }

            final OrderAcceptedMessage acceptedMessage = new OrderAcceptedMessage(order.getId());
            log.info("Sending order accepted event with id: {} ", order.getId());
            streamBridge.send("order-accepted", acceptedMessage);
        };
    }
}
