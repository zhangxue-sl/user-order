package com.example.usersystem.component;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.val;

@Component
public class JwtTokenProvider {
    @Value("{${security.jwt.token.secret-key:secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}") 
    private long validityInMilliseconds; 

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {

        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String password) {

     
        Claims claims = Jwts.claims().setSubject(username);
      
        claims.put("password", password);
  
        Date now = new Date();
     
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact(); 
    }


    public Authentication getAuthentication(String token) throws AuthenticationException {
     
        val usernameAndPassword = getUsernameAndPassword(token);
   
        val userDetails = userDetailsService.loadUserByUsername(usernameAndPassword.getFirst());
        if (userDetails.getPassword().equals(usernameAndPassword.getSecond())) {
            return new PreAuthenticatedAuthenticationToken(usernameAndPassword.getFirst(),
                    usernameAndPassword.getSecond(),
                 
                    userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Username and password don't match");
        }
    }


    public Pair<String,String> getUsernameAndPassword(String token) {
    
        val body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        val username = body.getSubject();
        val password = body.get("password", String.class);
     
        return Pair.of(username, password);
    }

  
    public String resolveToken(HttpServletRequest req) {
       
        String bearerToken = req.getHeader("Authorization"); 
       
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
          
            return bearerToken.substring(7, bearerToken.length());
        }
        return null; 
    }


    public boolean validateToken(String token) throws AuthenticationException {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Expired or invalid JWT token");
        }
    }

}