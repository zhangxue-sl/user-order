package com.example.usersystem;

import com.example.usersystem.domain.User;
import com.example.usersystem.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitUpRunner implements ApplicationRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        
        userRepository.save(User.builder().id("1111111").username("admin").password(passwordEncoder.encode("admin")).enabled(true).build());
    }
}