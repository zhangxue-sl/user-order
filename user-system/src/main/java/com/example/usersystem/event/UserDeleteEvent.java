package com.example.usersystem.event;

import lombok.Value;

@Value
public class UserDeleteEvent {
    private final String id;
    private final String username;
    private final String password;
    private final boolean enabled;
    
}