package devandagile.customermodule.config;

import devandagile.customermodule.service.OAuth2TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

import java.util.Properties;

@Configuration
public class MailConfig {

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		// Configure the mail server
		mailSender.setHost("smtp.example.com"); // replace with your SMTP server
		mailSender.setPort(587); // replace with your SMTP port

		// Set additional properties
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); // Use TLS
		props.put("mail.debug", "true"); // Enable debug output

		return mailSender;
	}

	@Bean
	public OAuth2TokenService oAuth2TokenService(OAuth2AuthorizedClientService authorizedClientService) {
		return new OAuth2TokenService(authorizedClientService) {
			@Override
			public String getAccessToken(OAuth2AuthorizedClient authorizedClient) {
				if (authorizedClient == null) {
					throw new IllegalStateException("OAuth2AuthorizedClient is null");
				}
				if (authorizedClient.getAccessToken() == null) {
					throw new IllegalStateException("AccessToken is null");
				}
				return authorizedClient.getAccessToken().getTokenValue();
			}
		};
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		ClientRegistration googleClientRegistration = ClientRegistration
				.withRegistrationId("google")
				.clientId("428517625913-c5mvsjiqr0esdegu6dfrjla9calrq5dq.apps.googleusercontent.com")
				.clientSecret("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")
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.redirectUri("http://localhost:7075/login/oauth2/code/{registrationId}")
				.scope("email", "profile")
				.authorizationUri("https://accounts.google.com/o/oauth2/auth")
				.tokenUri("https://oauth2.googleapis.com/token")
				.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
				.userNameAttributeName(IdTokenClaimNames.SUB)
				.clientName("Google")
				.build();

		return new InMemoryClientRegistrationRepository(googleClientRegistration);
	}
}
