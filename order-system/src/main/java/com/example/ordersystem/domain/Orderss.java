package com.example.ordersystem.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Orderss implements Serializable{
    static private final long serialVersionUID = -1L;
    
    @Id
    private String id;
    private String name;
    private Integer count;

    
}