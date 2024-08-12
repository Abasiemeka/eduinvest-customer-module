package devandagile.customermodule.service.serviceImpl;

import devandagile.customermodule.model.dto.SimpleMailDTO;
import devandagile.customermodule.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender javaMailSender;

	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	@Async
	public void sendSimpleMailMessage(SimpleMailDTO simpleMailDto) {


		SimpleMailMessage newMail = new SimpleMailMessage();
		newMail.setTo(simpleMailDto.to());
		newMail.setFrom("");
		newMail.setSubject(simpleMailDto.subject());
		newMail.setText(simpleMailDto.text());

		javaMailSender.send(newMail);
	}
}
