package com.example.ordersystem.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.example.ordersystem.command.CreateOrderCommand;
import com.example.ordersystem.command.DeleteOrderCommand;
import com.example.ordersystem.domain.Orderss;
import com.example.ordersystem.dto.CreateOrderDto;
import com.example.ordersystem.dto.DeleteOrderDto;
import com.example.ordersystem.event.OrderCreateEvent;
import com.example.ordersystem.event.OrderDeleteEvent;
import com.example.ordersystem.repository.OrderRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCommandService {
    @Autowired
   private OrderRepository orderRepository;

    @Autowired
    private CommandGateway commandGateway;

    public CompletableFuture<String> createOrder(CreateOrderDto createOrderDto){

        return commandGateway.send(new CreateOrderCommand(UUID.randomUUID().toString(),createOrderDto.getName(),createOrderDto.getCount()));
        
    }
    public CompletableFuture<Void> deleteOrder(DeleteOrderDto deleteOrderDto){

        return commandGateway.send(new DeleteOrderCommand(deleteOrderDto.getId(),deleteOrderDto.getName(),deleteOrderDto.getCount()));
    }

    @EventHandler
    void handle(OrderCreateEvent event){
        Orderss order = new Orderss();
        order.setId(event.getId());
        order.setName(event.getName());
        order.setCount(event.getCount());
        orderRepository.save(order);

    }
    @EventHandler
    void handle(OrderDeleteEvent event){
        Orderss order=orderRepository.findById(event.getId()).get();
        order.setName(event.getName());
        order.setCount(event.getCount());
        orderRepository.save(order);
    }
}