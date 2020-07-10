package com.example.ordersystem.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private final String id;
    private final String name;
    private final Integer count;
}