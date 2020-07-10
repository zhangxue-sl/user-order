package com.example.ordersystem.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.example.ordersystem.aggregate.OrderAggregate;
import com.example.ordersystem.domain.Orderss;
import com.example.ordersystem.query.GetOrderAggregaeteQuery;
import com.example.ordersystem.repository.OrderRepository;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderQueryService {
    @Autowired
    private EventStore eventStore;

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private OrderRepository orderRepository;

    
    public Optional<Orderss> findOrder(String orderId){

        return orderRepository.findById(orderId);
    }
    
    public void deleteOrder(String orderId){

         orderRepository.deleteById(orderId);;
    }

    public CompletableFuture<Optional<OrderAggregate>> getOrderAggreate(String orderAggregateId, Long expectedVersion){
        return queryGateway.query(new GetOrderAggregaeteQuery(orderAggregateId, expectedVersion) , ResponseTypes.optionalInstanceOf(OrderAggregate.class));

    }

    @QueryHandler
    public CompletableFuture<OrderAggregate> handle(GetOrderAggregaeteQuery getOrderAggregateQuery){
        CompletableFuture<OrderAggregate> future = new CompletableFuture<OrderAggregate>();
        if(getOrderAggregateQuery.getExpectedVersion()==null){
           
            EventSourcingRepository.builder(OrderAggregate.class).eventStore(eventStore).build()
            .load(getOrderAggregateQuery.getPostAggregateId()).execute(future::complete);
        }
        else{
          
            EventSourcingRepository.builder(OrderAggregate.class).eventStore(eventStore).eventStreamFilter(event->event.getSequenceNumber()<=getOrderAggregateQuery.getExpectedVersion()).build()
            .load(getOrderAggregateQuery.getPostAggregateId()).execute(future::complete);
        }
        //返回版本信息
        return future;
    }
    
}