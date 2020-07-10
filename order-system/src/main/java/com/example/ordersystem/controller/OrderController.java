package com.example.ordersystem.controller;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


import com.example.ordersystem.domain.Orderss;
import com.example.ordersystem.dto.CreateOrderDto;
import com.example.ordersystem.dto.DeleteOrderDto;
import com.example.ordersystem.repository.OrderRepository;
import com.example.ordersystem.service.OrderCommandService;
import com.example.ordersystem.service.OrderQueryService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    

    @Autowired
    private OrderCommandService orderCommandService;

    @Autowired
    private OrderQueryService orderQueryService;

    @Autowired
    OrderRepository orderRepository;

    @PostMapping(value = "createOrder")
    public CompletableFuture<String> createOrder(@RequestBody CreateOrderDto createOrderDto) {
        return orderCommandService.createOrder(createOrderDto);

    }

    @PostMapping(value = "deleteOrder")
    public CompletableFuture<Object> deleteOrder(@RequestBody DeleteOrderDto deleteOrderDto) {
        return orderCommandService.deleteOrder(deleteOrderDto)
                // thenApply：等待上一个的结果，并用到下一个：上个任务完成
                .thenApply((a) -> orderQueryService.getOrderAggreate(deleteOrderDto.getId(), null));

    }

    @GetMapping(value = "getOrder")
    public Optional<Orderss> getActivity(@RequestParam String orderId) {

        return orderQueryService.findOrder(orderId);
    }

    //delete
    //@GetMapping(value = "deleteOrder")
    @RabbitListener(queues="#{autoDeleteQueue1.name}")
    public void deleteOrder(@RequestParam String orderId) {

         orderQueryService.deleteOrder(orderId);
         
    }


}