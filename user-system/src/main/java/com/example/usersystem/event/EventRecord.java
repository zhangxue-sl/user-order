package com.example.usersystem.event;

import lombok.Value;

@Value
public class EventRecord {
    
    private final String type;

   
    private final long sequenceNumber;


    private final Object content;
}