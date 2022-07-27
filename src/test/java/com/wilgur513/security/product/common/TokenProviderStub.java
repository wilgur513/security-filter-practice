package com.wilgur513.security.product.common;

import com.wilgur513.security.authentication.provider.TokenProvider;
import org.springframework.stereotype.Component;

@Component
public class TokenProviderStub implements TokenProvider {

    @Override
    public boolean validate(String token) {
        return !token.contains("invalid");
    }

    @Override
    public Object extract(String token) {
        if (token.contains("valid")) {
            return "subject";
        }
        return null;
    }
}
