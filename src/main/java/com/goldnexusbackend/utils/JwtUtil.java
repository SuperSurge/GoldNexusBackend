package com.goldnexusbackend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.goldnexusbackend.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    //生成token
    public String generateToken(User user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .sign(Algorithm.HMAC256(secret));
    }

    public String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getSubject();
    }

    public Long getUserIdFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getClaim("userId")
                .asLong();
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
