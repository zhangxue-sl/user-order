package com.example.usersystem.service;

import com.example.usersystem.domain.User;
import com.example.usersystem.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findOneByUsername(username)
    // 如果值存在，返回包含的值；否則抛出异常:"can't find"。
    .orElseThrow(() -> new UsernameNotFoundException("can't find " + username));

  }
}