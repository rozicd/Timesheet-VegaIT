package com.example.vegacalendar.security;

import com.example.vegacalendar.application.exceptionModels.ExceptionModel;
import com.example.vegacalendar.core.exceptions.JwtExpiredException;
import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.TokenPrincipalModel;
import com.example.vegacalendar.core.model.UserModel;
import com.example.vegacalendar.core.model.UserType;
import com.example.vegacalendar.core.services.JwtService;
import com.example.vegacalendar.services.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserServiceImpl userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ExpiredJwtException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        UUID userId = null;
        UserType role = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try{
                username = jwtService.extractUsername(token);
                userId = jwtService.extractUserId(token);
                role = jwtService.extractRole(token);
            }catch (ExpiredJwtException e){
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(new ExceptionModel(e.getMessage()));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(json);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userService.loadUserByUsername(username);
            TokenPrincipalModel tokenPrincipalModel = new TokenPrincipalModel(userId, username, role);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(tokenPrincipalModel, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


        }
        filterChain.doFilter(request, response);
    }

}
