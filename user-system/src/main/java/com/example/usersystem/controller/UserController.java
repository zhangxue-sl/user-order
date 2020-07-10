package com.example.usersystem.controller;

import java.util.concurrent.CompletableFuture;

import com.example.usersystem.dto.DeleteUserDto;
import com.example.usersystem.service.UserCommandService;
import com.example.usersystem.service.UserQueryService;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserCommandService userCommandService;

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange diret;

    @PostMapping(value = "deleteUser")
    public CompletableFuture<Object> deleteUser(@RequestBody DeleteUserDto deleteUserDto) {
        return userCommandService.deleteUser(deleteUserDto)
                .thenApply((a) -> userQueryService.getUserAggreate(deleteUserDto.getId(), null));

    }

    @GetMapping(value = "deleteById")
    public void deleteById(String userId) {

        userQueryService.deleteUser(userId);

        String key = "order";
        String message = "delete";
        template.convertAndSend(diret.getName(), key, message);

        
    }

}