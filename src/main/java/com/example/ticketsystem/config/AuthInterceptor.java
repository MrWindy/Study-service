package com.example.ticketsystem.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String SECRET_KEY = "secret-key";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // Пропускаем публичные эндпоинты
        if (request.getRequestURI().startsWith("/api/auth") ||
                request.getRequestURI().startsWith("/api/tickets/events")) {
            return true;
        }

        // Проверка токена
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            token = token.substring(7); // Убираем "Bearer "
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            // Извлекаем ID пользователя и добавляем в атрибуты запроса
            Long userId = claims.get("userId", Long.class);
            request.setAttribute("userId", userId);

            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}