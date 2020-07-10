package com.example.ordersystem.event;

import lombok.Value;

@Value
public class OrderCreateEvent {
    private final String id;
    private final String name;
    private final Integer count;
}