package com.abasiemeka.jwtpractice.member.model.dto;

import lombok.Builder;

@Builder
public record MemberSignupDto(
		String name,
		String email,
		String password
) {
}
