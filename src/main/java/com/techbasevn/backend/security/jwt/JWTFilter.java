package com.techbasevn.backend.security.jwt;

import com.techbasevn.backend.security.webconfig.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@AllArgsConstructor
public class JWTFilter extends HttpFilter {
    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        tokenProvider.setAuthenticationByToken(resolveToken(httpServletRequest));
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
        String token = "Bearer ";
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(token)) {
            return null;
        }
        return bearerToken.substring(token.length());
    }


}
