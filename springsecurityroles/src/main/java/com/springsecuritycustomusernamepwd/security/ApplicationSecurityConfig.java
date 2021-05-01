package com.springsecuritycustomusernamepwd.security;

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
import static com.springsecuritycustomusernamepwd.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
			.antMatchers("/api/**").hasRole(STUDENT.name())
			.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
			.antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
			.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
			.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMINTRAINEE.name(),ADMIN.name())
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails studentUser = User.builder().username("studuser").password(passwordEncoder.encode("password"))
				.roles(STUDENT.name()).build(); // ROLE_STUDENT

		UserDetails adminUser = User.builder().username("adminuser").password(passwordEncoder.encode("password123"))
				.roles(ADMIN.name()).build(); // ROLE_ADMIN

		UserDetails admintraineeuser = User.builder().username("admintraineeuser")
				.password(passwordEncoder.encode("password123")).roles(ADMINTRAINEE.name()).build(); // ROLE_ADMINTRAINEE

		return new InMemoryUserDetailsManager(studentUser, adminUser, admintraineeuser);

	}
}
