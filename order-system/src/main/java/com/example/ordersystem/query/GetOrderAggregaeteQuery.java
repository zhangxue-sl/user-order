package com.example.ordersystem.query;

import lombok.Value;

@Value
public class GetOrderAggregaeteQuery {

    private final String postAggregateId;
    private final Long expectedVersion;
}