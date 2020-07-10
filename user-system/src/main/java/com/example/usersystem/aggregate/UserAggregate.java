package com.example.usersystem.aggregate;

import com.example.usersystem.command.DeleteUserCommand;
import com.example.usersystem.event.UserDeleteEvent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Data;

@Aggregate
@Data
public class UserAggregate {
    @AggregateIdentifier
    String id;
    String username;
    String password;
    boolean enabled;

    public UserAggregate() {

    }

    @CommandHandler
    public void handle(DeleteUserCommand comm) {
        AggregateLifecycle
                .apply(new UserDeleteEvent(comm.getId(), comm.getUsername(), comm.getPassword(), comm.isEnabled()));
    }

    @EventSourcingHandler
    public void onHandle(UserDeleteEvent event) {
        this.id = event.getId();
        this.username = event.getUsername();
        this.password = event.getPassword();
        this.enabled = event.isEnabled();

    }
}