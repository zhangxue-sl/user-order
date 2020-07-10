package com.example.usersystem.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

/**
 * delete user
 */
@Value
public class DeleteUserCommand {
    @TargetAggregateIdentifier
    private final String id;
    private final String username;
    private final String password;
    private final boolean enabled;
    
}