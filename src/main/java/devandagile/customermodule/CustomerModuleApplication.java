package devandagile.customermodule;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CustomerModuleApplication {
	
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		System.setProperty("SERVER_FULL_URL", dotenv.get("SERVER_FULL_URL"));

		System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
		System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));

		System.setProperty("DATASOURCE_URL", dotenv.get("DATASOURCE_URL"));
		System.setProperty("DATASOURCE_USERNAME", dotenv.get("DATASOURCE_USERNAME"));
		System.setProperty("DATASOURCE_PASSWORD", dotenv.get("DATASOURCE_PASSWORD"));

		System.setProperty("PAYSTACK_SECRET_KEY", dotenv.get("PAYSTACK_SECRET_KEY"));
		System.setProperty("PAYSTACK_PUBLIC_KEY", dotenv.get("PAYSTACK_PUBLIC_KEY"));
		System.setProperty("PAYSTACK_INITIALIZE_URL", dotenv.get("PAYSTACK_INITIALIZE_URL"));
		System.setProperty("PAYSTACK_VERIFY_URL", dotenv.get("PAYSTACK_VERIFY_URL"));
		System.setProperty("PAYSTACK_BASE_URL", dotenv.get("PAYSTACK_BASE_URL"));

		SpringApplication.run(CustomerModuleApplication.class, args);
	}
}