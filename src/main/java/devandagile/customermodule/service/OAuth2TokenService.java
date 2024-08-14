package devandagile.customermodule.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

@Service
public class OAuth2TokenService {

	private final OAuth2AuthorizedClientService authorizedClientService;

	public OAuth2TokenService(OAuth2AuthorizedClientService authorizedClientService) {
		this.authorizedClientService = authorizedClientService;
	}

	// The method to get the access token from the OAuth2AuthorizedClient
	public String getAccessToken(OAuth2AuthorizedClient authorizedClient) {
		return authorizedClient.getAccessToken().getTokenValue();
	}
}
