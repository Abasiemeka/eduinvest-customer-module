package devandagile.customermodule.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaystackConfig {

	private final Dotenv dotenv = Dotenv.load();

	@Getter
	@Value("${paystack.secret.key}")
	private String paystackSecretKey;

	@PostConstruct
	public void init() {
		String secretKey = dotenv.get("PAYSTACK_SECRET_KEY");
		if (secretKey != null) {
			this.paystackSecretKey = secretKey;
		}
	}
}
