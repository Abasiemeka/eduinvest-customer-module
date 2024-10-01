package com.abasiemeka.jwtpractice.member.service;

import com.abasiemeka.jwtpractice.eventManagement.event.EmailVerificationRequestEvent;
import com.abasiemeka.jwtpractice.member.model.Member;
import com.abasiemeka.jwtpractice.member.model.MemberAccount;
import com.abasiemeka.jwtpractice.member.model.dto.LoginRequestDto;
import com.abasiemeka.jwtpractice.member.model.dto.MemberSignupDto;
import com.abasiemeka.jwtpractice.member.repository.MemberRepository;
import org.apache.catalina.CredentialHandler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final ApplicationEventPublisher eventPublisher;
	private final AuthenticationManager authenticationManager;

	public MemberDetailsService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher, AuthenticationManager authenticationManager) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
		this.eventPublisher = eventPublisher;
		this.authenticationManager = authenticationManager;
	}

	public UserDetails loadUserByUsername(String username) {
		Member member = memberRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

		return MemberAccount
				.builder()
				.username(member.getEmail())
				.password(member.getPassword())
				.roles(List.of(member.getRole()))
				.isAccountNonExpired(member.isNonExpired())
				.isAccountNonLocked(member.isNonLocked())
				.isCredentialsNonExpired(member.isCredentialsNonExpired())
				.isEnabled(member.isEnabled())
				.build();
	}

	public ResponseEntity<String> register(MemberSignupDto memberSignupDto) {
		if (userExists(memberSignupDto.email())) {
			return ResponseEntity.badRequest().body("Member creation failed. Email address already in use!");
		}
		else {
			try { Member member = Member.builder()
					.name(memberSignupDto.name())
					.email(memberSignupDto.email())
					.password(passwordEncoder.encode(memberSignupDto.password()))
					.verificationToken(UUID.randomUUID().toString())
					.role("USER")
					.isEnabled(true)
					.isNonExpired(true)
					.isNonLocked(true)
					.isCredentialsNonExpired(true)
					.build();
				memberRepository.save(member);
				eventPublisher.publishEvent(new EmailVerificationRequestEvent(this, member.getEmail(), member.getVerificationToken()));
				return ResponseEntity.ok("Member " + member.getName() + " created. Awaiting email verification.");
			} catch (Exception e) {
				return ResponseEntity.badRequest().body("Error creating member");
			}
		}
	}

	public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password())
			);

			if (authentication.isAuthenticated()) {
				return ResponseEntity.ok("Login successful for user: " + loginRequestDto.email());
			} else {
				return ResponseEntity.status(401).body("Authentication failed");
			}
		} catch (AuthenticationException e) {
			return ResponseEntity.status(401).body("Invalid username or password");
		}
	}

	private boolean userExists(String email) {
		return memberRepository.existsByEmail(email);
	}
}
