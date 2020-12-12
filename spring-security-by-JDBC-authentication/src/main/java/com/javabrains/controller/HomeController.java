package com.javabrains.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "<h2> Home page <h2>";
	}
	
	@GetMapping("/user")
	public String user() {
		return "<h2> User page </h2>";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "</h2>Welcome to Admin page</h2>";
	}
	
	@GetMapping("/**")
	public String error() {
		return "</h2>Sorry, wrong page visited </h2>";
	}
	
	
}
