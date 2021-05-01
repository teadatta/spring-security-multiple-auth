package com.springsecuritypreauthorized;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityPreAuthorizeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityPreAuthorizeApplication.class, args);
	}

}
