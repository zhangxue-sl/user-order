package com.example.ordersystem.event;

import lombok.Value;

@Value
public class EventRecord {
    private final String type;

    //序列号、版本号
    private final long sequenceNumber;


    private final Object content;
}