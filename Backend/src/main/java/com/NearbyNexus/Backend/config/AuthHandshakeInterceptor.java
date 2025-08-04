package com.NearbyNexus.Backend.config;

import com.NearbyNexus.Backend.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.Accessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.Map;

@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {


    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpRequest = servletRequest.getServletRequest();

            // Try to extract token from Authorization header
            String token = httpRequest.getHeader("Authorization");

            // Fallback: Try to extract token from URL query parameter
            if (token == null) {
                token = httpRequest.getParameter("token");
            }

            // Remove "Bearer " prefix if present
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            if (token != null && jwtUtil.validateToken(token)) {
                String username = jwtUtil.getEmailFromToken(token);
                attributes.put("username", username); // just store for now
                return true;
            }

        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {}
}
