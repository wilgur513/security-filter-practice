package com.wilgur513.security.product.config;

import com.wilgur513.security.config.annotation.SecurityConfiguration;
import com.wilgur513.security.filter.matcher.RequireAuthenticationRequestMatcher;
import com.wilgur513.security.filter.matcher.RequireAuthenticationRequestMatcherBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SecurityConfiguration
public class TestSecurityConfig {

    @Bean
    public RequireAuthenticationRequestMatcher builder() {
        return new RequireAuthenticationRequestMatcherBuilder()
                .permitAll("/all")
                .permitAll(HttpMethod.POST, "/check-token")
                .build();
    }
}
