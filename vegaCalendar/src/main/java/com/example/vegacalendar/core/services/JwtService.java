package com.example.vegacalendar.core.services;

import com.example.vegacalendar.core.model.UserModel;
import com.example.vegacalendar.core.model.UserType;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public interface JwtService {
    String generateToken(UserModel userModel);
    String extractUsername(String token);
    Date extractExpiration(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    Boolean validateToken(String token, UserDetails userDetails);
    UserType extractRole(String token);

    UUID extractUserId(String token);

}
