package devandagile.customermodule.service.serviceImpl;

import devandagile.customermodule.config.exception.Exceptions.EmailSendingException;
import devandagile.customermodule.model.dto.SimpleMailDTO;
import devandagile.customermodule.service.EmailServiceOAuth;
import devandagile.customermodule.service.OAuth2TokenService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceOAuthImpl implements EmailServiceOAuth {

	private final JavaMailSender mailSender;
	private final OAuth2TokenService oAuth2TokenService;
	private final OAuth2AuthorizedClientService authorizedClientService;
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceOAuthImpl.class);

	public EmailServiceOAuthImpl(JavaMailSender mailSender, OAuth2TokenService oAuth2TokenService, OAuth2AuthorizedClientService authorizedClientService) {
		this.mailSender = mailSender;
		this.oAuth2TokenService = oAuth2TokenService;
		this.authorizedClientService = authorizedClientService;
	}

	@Override
	public void sendEmail(SimpleMailDTO simpleMailDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw new IllegalStateException("User is not authenticated");
		}

		OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
				"google", authentication.getName());

		if (authorizedClient == null) {
			throw new IllegalStateException("OAuth2AuthorizedClient is null");
		}

		if (authorizedClient.getAccessToken() == null) {
			throw new IllegalStateException("AccessToken is null");
		}

		MimeMessagePreparator messagePreparator = getMimeMessagePreparator(simpleMailDTO, authorizedClient);

		try {
			mailSender.send(messagePreparator);
			logger.info("Email sent successfully.");
		} catch (MailException e) {
			logger.error("Email sending failed.", e);
			throw new EmailSendingException(e.getMessage() + " " + e.getRootCause(), e);
		}
	}

	@NotNull
	private static MimeMessagePreparator getMimeMessagePreparator(SimpleMailDTO simpleMailDTO, OAuth2AuthorizedClient authorizedClient) {

		String oauthToken = authorizedClient.getAccessToken().getTokenValue();

		return mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("abasiemeka@gmail.com");
			messageHelper.setTo(simpleMailDTO.to());
			messageHelper.setSubject(simpleMailDTO.subject());
			messageHelper.setText(simpleMailDTO.text());

			// Some SMTP servers might not use Authorization header; verify if necessary
			mimeMessage.addHeader("Authorization", "Bearer " + oauthToken);
		};
	}
}

