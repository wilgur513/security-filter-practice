package com.wilgur513.security.authentication;

public class Authentication {

    private final Object principle;

    public Authentication(Object principle) {
        this.principle = principle;
    }

    public Object getPrinciple() {
        return principle;
    }
}
