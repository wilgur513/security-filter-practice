package com.wilgur513.security.filter.matcher;

import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequireAuthenticationRequestMatcher {

    private final List<PermittedRequest> permittedRequests;

    public RequireAuthenticationRequestMatcher() {
        this.permittedRequests = Collections.emptyList();
    }

    public RequireAuthenticationRequestMatcher(Map<HttpMethod, List<String>> paths) {
        this.permittedRequests = paths.keySet().stream()
                .flatMap(method -> permittedRequests(paths, method))
                .collect(Collectors.toList());
    }

    private Stream<PermittedRequest> permittedRequests(Map<HttpMethod, List<String>> paths, HttpMethod method) {
        return paths.get(method).stream()
                .map(path -> new PermittedRequest(path, method));
    }

    public boolean isRequireAuthentication(HttpServletRequest request) {
        return permittedRequests.stream()
                .noneMatch(permittedRequest -> permittedRequest.match(request));
    }
}
