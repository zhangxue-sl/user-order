package com.example.usersystem.dto;

import lombok.Data;

@Data
public class DeleteUserDto {
    
    private String id;
    private String username;
    private String password;
    private boolean enabled;


}