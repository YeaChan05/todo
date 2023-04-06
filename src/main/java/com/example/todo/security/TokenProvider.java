package com.example.todo.security;

import com.example.todo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY="eyJhbGciOiJIUzUxMiJ9.eyJJc3N1ZXIiOiJ0b2RvIGFwcCIsIlVzZXJuYW1lIjoiSmF2YUluVXNlIiwiZXhwIjoxNjgwNTkzNDA0LCJpYXQiOjE2ODA1OTMzNTgsIlN1YmplY3QiOiJxa2VucmRsIn0.NuQBca3f7nMCtW_qTZi6wu_DZhGK5NItcK1QSMnu4yjCdkg4R5V_Uw_toSuPqpwPUaDXQb0gKn82Ydx_Rrt91Q";

    public String create(UserEntity userEntity){
        Date expireDate=Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .setSubject(userEntity.getId())
                .setIssuer("todo app")
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();

    }

    public String validateAndGetUserId(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
