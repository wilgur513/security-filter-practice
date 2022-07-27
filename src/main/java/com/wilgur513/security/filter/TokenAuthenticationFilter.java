package com.wilgur513.security.filter;

import com.wilgur513.security.authentication.Authentication;
import com.wilgur513.security.authentication.SecurityContext;
import com.wilgur513.security.authentication.SecurityContextHolder;
import com.wilgur513.security.authentication.provider.TokenProvider;
import com.wilgur513.security.filter.exception.UnauthorizedException;
import com.wilgur513.security.filter.matcher.RequireAuthenticationRequestMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityContext securityContext;
    private final TokenProvider tokenProvider;
    private final RequireAuthenticationRequestMatcher matcher;

    public TokenAuthenticationFilter(SecurityContext securityContext, TokenProvider tokenProvider, RequireAuthenticationRequestMatcher requireAuthenticationRequestMatcher) {
        this.securityContext = securityContext;
        this.tokenProvider = tokenProvider;
        this.matcher = requireAuthenticationRequestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (matcher.isRequireAuthentication(request)) {
                doAuthentication(request);
            }

            filterChain.doFilter(request, response);
        } finally {
            clearAuthenticatedPrinciple();
        }
    }

    private void doAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null || !tokenProvider.validate(token)) {
            throw new UnauthorizedException();
        }

        setAuthenticatedPrinciple(token);
    }

    private void setAuthenticatedPrinciple(String token) {
        Object principle = tokenProvider.extract(token);
        securityContext.setAuthentication(new Authentication(principle));
    }

    private void clearAuthenticatedPrinciple() {
        securityContext.setAuthentication(null);
    }
}
