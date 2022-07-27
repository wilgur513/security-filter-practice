package com.wilgur513.security.filter.matcher;

import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequireAuthenticationRequestMatcherBuilder {

    private static final HttpMethod[] METHODS = {HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE,
            HttpMethod.PUT, HttpMethod.PATCH};

    private final Map<HttpMethod, List<String>> permitAllPaths;

    public RequireAuthenticationRequestMatcherBuilder() {
        this.permitAllPaths = Stream.of(METHODS)
                .collect(Collectors.toMap(method -> method, method -> new ArrayList<>()));
    }

    public RequireAuthenticationRequestMatcherBuilder permitAll(String... urls) {
        for (HttpMethod method : METHODS) {
            addToPermittedUrls(method, urls);
        }
        return this;
    }

    public RequireAuthenticationRequestMatcherBuilder permitAll(HttpMethod method, String... urls) {
        addToPermittedUrls(method, urls);
        return this;
    }

    private void addToPermittedUrls(HttpMethod method, String[] urls) {
        List<String> permittedUrls = permitAllPaths.get(method);
        permittedUrls.addAll(List.of(urls));
    }

    public RequireAuthenticationRequestMatcher build() {
        return new RequireAuthenticationRequestMatcher(permitAllPaths);
    }
}
