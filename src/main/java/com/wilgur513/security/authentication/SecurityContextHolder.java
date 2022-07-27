package com.wilgur513.security.authentication;

public class SecurityContextHolder {

    private static final ThreadLocal<SecurityContext> SECURITY_CONTEXT = new ThreadLocal<>();

    public static SecurityContext getContext() {
        if (SECURITY_CONTEXT.get() == null) {
            SECURITY_CONTEXT.set(new SecurityContext());
        }

        return SECURITY_CONTEXT.get();
    }

    public static void clear() {
        SECURITY_CONTEXT.remove();
    }
}
