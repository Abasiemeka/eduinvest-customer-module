package devandagile.customermodule.service.serviceImpl;

import devandagile.customermodule.config.exception.Exceptions.EmailSendingException;
import devandagile.customermodule.model.dto.SimpleMailDTO;
import devandagile.customermodule.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	private final JavaMailSender javaMailSender;

	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	@Async
	public void sendSimpleMailMessage(SimpleMailDTO simpleMailDto) {
		try {
			SimpleMailMessage newMail = new SimpleMailMessage();
			newMail.setTo(simpleMailDto.to());
			newMail.setFrom("abasiemeka@gmail.com");
			newMail.setSubject(simpleMailDto.subject());
			newMail.setText(simpleMailDto.text());

			javaMailSender.send(newMail);
			logger.info("Email Successfully sent.");
		} catch (MailException e) {
			logger.info("Email sending failed.");
			throw new EmailSendingException(e.getMessage() + " " + e.getRootCause(), e);
		}
	}
}
