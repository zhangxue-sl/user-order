package com.example.ordersystem.event;

import lombok.Value;

@Value
public class OrderDeleteEvent {
    private final String id;
    private final String name;
    private final Integer count;
}