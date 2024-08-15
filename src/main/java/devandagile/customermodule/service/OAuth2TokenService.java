package devandagile.customermodule.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

@Service
public class OAuth2TokenService {

	private final OAuth2AuthorizedClientService authorizedClientService;
	private static final Logger logger = LoggerFactory.getLogger(OAuth2TokenService.class);

	public OAuth2TokenService(OAuth2AuthorizedClientService authorizedClientService) {
		this.authorizedClientService = authorizedClientService;
	}

	// The method to get the access token from the OAuth2AuthorizedClient
	public String getAccessToken(OAuth2AuthorizedClient authorizedClient) {
		if (authorizedClient == null) {
			logger.error("OAuth2AuthorizedClient is null.");
			throw new IllegalArgumentException("OAuth2AuthorizedClient cannot be null");
		}

		if (authorizedClient.getAccessToken() == null) {
			logger.error("AccessToken is null for the given OAuth2AuthorizedClient.");
			throw new IllegalStateException("AccessToken is null");
		}

		String tokenValue = authorizedClient.getAccessToken().getTokenValue();
		logger.debug("Access token retrieved: {}", tokenValue);
		return tokenValue;
	}
}
