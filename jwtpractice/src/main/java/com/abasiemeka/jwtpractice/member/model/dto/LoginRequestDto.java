package com.abasiemeka.jwtpractice.member.model.dto;

import lombok.Builder;

@Builder
public record LoginRequestDto(
		String email,
		String password
) {
}
