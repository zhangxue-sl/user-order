package com.example.usersystem.configuration;

import com.example.usersystem.component.JwtTokenFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtTokenFilter jwtTokenFilter;


    @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin().loginPage("/login.html").and().authorizeRequests()
    .antMatchers( "/login", "/index.html","/login.html", "/h2-console/**").permitAll()
    .anyRequest().authenticated().and().csrf().disable()
    .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
    .headers().frameOptions().disable();
}
    
}