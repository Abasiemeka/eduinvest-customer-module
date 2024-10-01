package com.abasiemeka.jwtpractice.member.controller;

import com.abasiemeka.jwtpractice.member.model.dto.LoginRequestDto;
import com.abasiemeka.jwtpractice.member.model.dto.MemberSignupDto;
import com.abasiemeka.jwtpractice.member.service.MemberDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {
	private final MemberDetailsService memberDetailsService;

	public MemberController(MemberDetailsService memberDetailsService) {
		this.memberDetailsService = memberDetailsService;
	}

	@GetMapping("/")
	public ResponseEntity<String> home() {
		return ResponseEntity.ok("Welcome!");
	}

	@GetMapping("/register")
	public ResponseEntity<String> register() {
		return ResponseEntity.ok("Please login or register new account.");
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody MemberSignupDto memberSignupDto) {
		return memberDetailsService.register(memberSignupDto);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
		return memberDetailsService.login(loginRequestDto);
	}
}
