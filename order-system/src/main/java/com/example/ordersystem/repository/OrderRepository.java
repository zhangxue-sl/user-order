package com.example.ordersystem.repository;


import com.example.ordersystem.domain.Orderss;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Orderss,String>{
    
}