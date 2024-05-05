package com.batteryapp.services.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * @author Alok kumar
 * 
 */
@RestController
@RequestMapping
public class TestController {
	
	@GetMapping("/test")
	public String testController(HttpServletResponse response, HttpServletRequest request) {
	
		return "This is test controller";
	}
}
