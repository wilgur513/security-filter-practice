package com.wilgur513.security.config;

import com.wilgur513.security.authentication.SecurityContext;
import com.wilgur513.security.authentication.provider.JwtTokenProvider;
import com.wilgur513.security.authentication.provider.TokenProvider;
import com.wilgur513.security.filter.ExceptionTranslationFilter;
import com.wilgur513.security.filter.TokenAuthenticationFilter;
import com.wilgur513.security.filter.matcher.RequireAuthenticationRequestMatcher;
import org.springframework.beans.BeansException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.servlet.Filter;

@Configuration
class FilterBeansConfiguration {

    @Bean
    @RequestScope
    public SecurityContext securityContext() {
        return new SecurityContext();
    }

    @Bean
    public FilterRegistrationBean<Filter> tokenAuthenticationFilter(GenericWebApplicationContext context) {
        TokenProvider tokenProvider = getTokenProviderBean(context);
        RequireAuthenticationRequestMatcher matcher = getRequireAuthenticationRequestMatcher(context);

        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>(
                new TokenAuthenticationFilter(securityContext(), tokenProvider, matcher)
        );
        registrationBean.setOrder(1);
        return registrationBean;
    }

    private TokenProvider getTokenProviderBean(GenericWebApplicationContext context) {
        try {
            return context.getBean(TokenProvider.class);
        } catch (BeansException e) {
            context.registerBean(JwtTokenProvider.class);
            return context.getBean(TokenProvider.class);
        }
    }

    private RequireAuthenticationRequestMatcher getRequireAuthenticationRequestMatcher(GenericWebApplicationContext context) {
        try {
            return context.getBean(RequireAuthenticationRequestMatcher.class);
        } catch (BeansException e) {
            context.registerBean(RequireAuthenticationRequestMatcher.class);
            return context.getBean(RequireAuthenticationRequestMatcher.class);
        }
    }

    @Bean
    public FilterRegistrationBean<Filter> exceptionTranslationFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>(new ExceptionTranslationFilter());
        registrationBean.setOrder(0);
        return registrationBean;
    }
}
