package com.example.usersystem.controller;

import javax.security.auth.message.AuthException;

import com.example.usersystem.component.JwtTokenProvider;
import com.example.usersystem.dto.LoginDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.val;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailsService userDetailsService;
   

    @Autowired
    PasswordEncoder passwordEncoder;

    
  @PostMapping("login")
  public String login(LoginDto loginDto) throws AuthException {
    System.out.println("sgsgsdg");
    val username = loginDto.getUsername();
    val password = loginDto.getPassword();
    System.out.println("loginDto:" + loginDto);
    val user = userDetailsService.loadUserByUsername(username); 
    System.out.println("user:" + user);
    if (passwordEncoder.matches(password, user.getPassword())) {
      System.out.println("进入if。。。。");
      String token = jwtTokenProvider.createToken(user.getUsername(), user.getPassword());
      System.out.println("token:" + token);
      return "Bearer " + token; 

    } else {
      throw new BadCredentialsException("password does not match");
    }
  }

}