package com.example.usersystem.repository;

import java.util.Optional;

import com.example.usersystem.domain.User;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

    Optional<User> findOneByUsername(String username);
}