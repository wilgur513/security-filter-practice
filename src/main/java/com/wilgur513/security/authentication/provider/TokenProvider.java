package com.wilgur513.security.authentication.provider;

public interface TokenProvider {

    boolean validate(String token);

    Object extract(String token);
}
