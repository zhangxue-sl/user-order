package com.example.usersystem.query;

import lombok.Value;

@Value
public class GetUserAggregateQuery {
    private final String postAggregateId;
    private final Long expectedVersion;
}