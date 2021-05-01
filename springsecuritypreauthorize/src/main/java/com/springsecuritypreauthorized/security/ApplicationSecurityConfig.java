package com.springsecuritypreauthorized.security;

import static com.springsecuritypreauthorized.security.ApplicationUserPermission.*;
import static com.springsecuritypreauthorized.security.ApplicationUserRole.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
			.antMatchers("/api/**").hasRole(STUDENT.name())
//			.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMINTRAINEE.name(),ADMIN.name())
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails studentUser = User.builder()
				.username("studuser")
				.password(passwordEncoder.encode("password"))
			//	.roles(STUDENT.name())// ROLE_STUDENT
				.authorities(STUDENT.getGrantedAuthorities())
				.build(); 

		UserDetails adminUser = User.builder()
				.username("adminuser")
				.password(passwordEncoder.encode("password123"))
			//	.roles(ADMIN.name()) // ROLE_ADMIN
				.authorities(ADMIN.getGrantedAuthorities())
				.build(); 

		UserDetails admintraineeuser = User.builder()
				.username("admintraineeuser")
				.password(passwordEncoder.encode("password123"))
			//	.roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
				.authorities(ADMINTRAINEE.getGrantedAuthorities())
				.build(); 
		
		return new InMemoryUserDetailsManager(studentUser, adminUser, admintraineeuser);

	}
}
