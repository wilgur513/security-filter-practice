package com.wilgur513.security;

import com.wilgur513.security.product.SecurityApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;

@SpringBootTest(
        classes = SecurityApplication.class
)
class SecurityApplicationTests {

    @Autowired
    ApplicationContext context;

    @Test
    void contextLoads() {
        System.out.println(context.getBean(FilterRegistrationBean.class));
    }

}
