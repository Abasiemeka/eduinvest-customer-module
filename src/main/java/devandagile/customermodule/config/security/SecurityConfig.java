package devandagile.customermodule.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> {
					// Allow unauthenticated access to signup and email verification
					auth.requestMatchers("/v1/customer/signup", "/v1/customer/verify-mail").permitAll();

					// Use wildcard to secure all routes under "/v1/customer/{id}/"
					auth.requestMatchers("/v1/customer/{id}/**").authenticated();

					// All other requests also require authentication
					auth.anyRequest().authenticated();
				})
				.oauth2Login(withDefaults()) // Enable OAuth2 login
				.oauth2Client(withDefaults()) // Enable OAuth2 client
				.csrf().disable() // Disable CSRF for APIs
				.build();
	}

	@Bean
	public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
	}
}
