package com.hanghae.thiscord_clone.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.thiscord_clone.exception.custom.CustomSecurityException;
import com.hanghae.thiscord_clone.exception.custom.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper om;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request);

        if (StringUtils.hasText(token)) {

            try {
                jwtUtil.validateToken(token);
                setAuthentication(token);
                filterChain.doFilter(request, response);
            } catch (InvalidCookieException e) {
                sendErrorMsg(e, response);
            }
        } else {

            filterChain.doFilter(request, response);
        }
    }

    private void setAuthentication(String token) {
        Authentication authentication = jwtUtil.getAuthentication(token);

        SecurityContext securityContext = SecurityContextHolder.getContext();

        securityContext.setAuthentication(authentication);
    }

    private void sendErrorMsg(Exception e, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        try {
            String result = om.writeValueAsString(new CustomSecurityException(ErrorCode.UNAUTHORIZED_ERROR));
             response.getWriter().write(result);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

}

