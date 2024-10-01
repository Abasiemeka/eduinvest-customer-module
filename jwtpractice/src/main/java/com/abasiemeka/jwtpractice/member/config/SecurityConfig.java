package com.abasiemeka.jwtpractice.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/error").permitAll()
						.requestMatchers("/api/v1/member/*").permitAll()
						.requestMatchers("/api/v1/admin/*").hasRole("ADMIN")
						.anyRequest().authenticated())
				.httpBasic(withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(withDefaults())
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){

          try {
              return authenticationConfiguration.getAuthenticationManager();
          } catch (Exception e) {
              throw new RuntimeException("Failed to get AuthenticationManager", e);
		  }
	}

}
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return memberDetailsService;
//	}

//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(userDetailsService());
//		authenticationProvider.setPasswordEncoder(passwordEncoder());
//		return authenticationProvider;
//	}
