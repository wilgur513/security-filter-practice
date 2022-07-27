package com.wilgur513.security.filter.matcher;

import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

final class PermittedRequest {

    private final String url;
    private final HttpMethod method;

    PermittedRequest(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
    }

    boolean match(HttpServletRequest request) {
        return url.equals(request.getServletPath()) && method.equals(resolveToHttpMethod(request));
    }

    private HttpMethod resolveToHttpMethod(HttpServletRequest request) {
        return HttpMethod.resolve(request.getMethod().toUpperCase());
    }
}
