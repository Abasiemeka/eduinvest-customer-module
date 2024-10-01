package com.abasiemeka.jwtpractice.eventManagement.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@Builder
public class EmailVerificationRequestEvent extends ApplicationEvent {
	private final String email;
	private final String verificationToken;

	public EmailVerificationRequestEvent(Object source, String email, String verificationToken) {
		super(source);
		this.email = email;
		this.verificationToken = verificationToken;
	}
}