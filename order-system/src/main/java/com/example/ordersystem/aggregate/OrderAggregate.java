package com.example.ordersystem.aggregate;

import com.example.ordersystem.command.CreateOrderCommand;
import com.example.ordersystem.command.DeleteOrderCommand;
import com.example.ordersystem.event.OrderCreateEvent;
import com.example.ordersystem.event.OrderDeleteEvent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Data;

@Aggregate
@Data
public class OrderAggregate {

    @AggregateIdentifier
    private String id;
    private String name;
    private Integer count;

    protected OrderAggregate() {

    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand comm){
        AggregateLifecycle.apply(new OrderCreateEvent(comm.getId(), comm.getName(),comm.getCount()));

    }

    @CommandHandler
    public void handle(DeleteOrderCommand comm) {
        AggregateLifecycle.apply(new OrderDeleteEvent(comm.getId(), comm.getName(),comm.getCount()));

    }

    @EventSourcingHandler
    public void onHandle(OrderCreateEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.count=event.getCount();

    }

    @EventSourcingHandler
    public void onHandle(OrderDeleteEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.count=event.getCount();
    }

}