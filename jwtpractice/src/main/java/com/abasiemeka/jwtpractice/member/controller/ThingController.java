package com.abasiemeka.jwtpractice.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/thing")
public class ThingController {
	@GetMapping("/1")
	public ResponseEntity<String> thing1() {
		return ResponseEntity.ok("Welcome to Thing 1");
	}
}
