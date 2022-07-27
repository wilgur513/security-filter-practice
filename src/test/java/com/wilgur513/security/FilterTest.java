package com.wilgur513.security;

import com.wilgur513.security.product.SecurityApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.is;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = SecurityApplication.class
)
public class FilterTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void validateToken() {
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "valid_token")
                .when().log().all()
                .get("/check-token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("subject", is("subject"));
    }

    @Test
    void validateInvalidToken() {
        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "invalid_token")
                .when().log().all()
                .get("/check-token")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void permitAllUrl() {
        RestAssured.given().log().all()
                .when().log().all()
                .get("/all")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("message", is("hello"));
    }

    @Test
    void permitCheckTokenByPost() {
        RestAssured.given().log().all()
                .when().log().all()
                .post("/check-token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("subject", is("subject"));
    }
}
