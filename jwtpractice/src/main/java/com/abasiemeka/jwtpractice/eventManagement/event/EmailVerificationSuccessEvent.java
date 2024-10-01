package com.abasiemeka.jwtpractice.eventManagement.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@Builder
public class EmailVerificationSuccessEvent extends ApplicationEvent {
	private final String email;

	public EmailVerificationSuccessEvent(Object source, String email) {
		super(source);
		this.email = email;
	}
}