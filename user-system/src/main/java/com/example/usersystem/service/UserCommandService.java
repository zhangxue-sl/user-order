package com.example.usersystem.service;

import java.util.concurrent.CompletableFuture;

import com.example.usersystem.command.DeleteUserCommand;
import com.example.usersystem.domain.User;
import com.example.usersystem.dto.DeleteUserDto;
import com.example.usersystem.event.UserDeleteEvent;
import com.example.usersystem.repository.UserRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCommandService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommandGateway commandGateway;

    public CompletableFuture<Void> deleteUser(DeleteUserDto deleteUserDto){
return commandGateway.send(new DeleteUserCommand(deleteUserDto.getId(), deleteUserDto.getUsername(),deleteUserDto.getPassword(),deleteUserDto.isEnabled()));
    }

    @EventHandler
    void handle(UserDeleteEvent event){
        User user=userRepository.findById(event.getId()).get();
        user.setUsername(event.getUsername());
        user.setPassword(event.getPassword());
        user.setEnabled(event.isEnabled());
    }
    
}