package com.wilgur513.security.product.controller;

import com.wilgur513.security.authentication.SecurityContext;
import com.wilgur513.security.authentication.SecurityContextHolder;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final SecurityContext context;

    public TokenController(SecurityContext context) {
        this.context = context;
    }

    @GetMapping("/check-token")
    public ResponseEntity<Map<String, String>> checkToken() {
        String subject = (String) context.getAuthentication().getPrinciple();
        return ResponseEntity.ok().body(Map.of("subject", subject));
    }

    @PostMapping("/check-token")
    public ResponseEntity<Map<String, String>> postCheckToken() {
        return ResponseEntity.ok().body(Map.of("subject", "subject"));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, String>> all() {
        return ResponseEntity.ok(Map.of("message", "hello"));
    }
}
