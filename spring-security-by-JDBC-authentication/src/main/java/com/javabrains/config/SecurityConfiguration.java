package com.javabrains.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/** 
		 * it's default configuration provided by spring security with Embedded H2 database, 
		 * with predefined schema 
		*/
		/*
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.withDefaultSchema()
		.withUser(
				User.withUsername("user")
				.password("pass")
				.roles("USER")
				)
		.withUser(
				User.withUsername("admin")
				.password("pass")
				.roles("ADMIN")
				);
		*/
		
		/**
		 * dataSource reference first check is there any embedded DB is there, then no need to 
		 * external configuration to connect to DB.
		 *  Here we use H2 Embedded database.
		 *  we provide and user defined schema with import data into it when application booting time.
		 *  all schema present inside resources file.
		 * */
		auth.jdbcAuthentication()
		.dataSource(dataSource);
	}
	
	/**
	 * Authorize each request URL coming from user, 
	 * which is allowed for to do this operation or not for respective role of user.
	 * */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.antMatchers("/admin").hasRole("ADMIN")
					.antMatchers("/user").hasAnyRole("ADMIN", "USER")
					.antMatchers("/").permitAll()
					.and().formLogin();
	}
	
	/**
	 * Password encryption mechanism to perform password must be decrypt,
	 * which is not available as a plane text format to store 
	 * */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
}
