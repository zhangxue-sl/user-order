package com.example.ordersystem.dto;

import lombok.Data;

@Data
public class DeleteOrderDto {
    private String id;
    private String name;
    private Integer count;
}