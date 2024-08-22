package devandagile.customermodule.service.serviceImpl;

import devandagile.customermodule.config.PaystackConfig;
import devandagile.customermodule.model.dto.PaystackPaymentRequestDTO;
import devandagile.customermodule.service.PaystackService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class PaystackServiceImpl implements PaystackService {

	private final String PAYSTACK_SECRET_KEY;
	private final PaystackConfig paystackConfig;
	private final RestTemplate restTemplate;

	public PaystackServiceImpl(@Value("${paystack.secret.key}") String paystackSecretKey, PaystackConfig paystackConfig, RestTemplate restTemplate) {
		PAYSTACK_SECRET_KEY = paystackSecretKey;
		this.paystackConfig = paystackConfig;
		this.restTemplate = restTemplate;
	}

	@Override
	public String initializePayment(PaystackPaymentRequestDTO paymentRequestInKobo) {
		String url = "https://api.paystack.co/transaction/initialize";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);
		headers.set("Content-Type", "application/json");

		HttpEntity<Object> requestEntity = new HttpEntity<>(paymentRequestInKobo, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

		return response.getBody();
	}

	public boolean verifyPayment(String reference) {
		String url = "https://api.paystack.co/transaction/verify/" + reference;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		// Logic to parse the response and verify the status
		return Objects.requireNonNull(response.getBody()).contains("\"status\": true");
	}

	// Method to verify webhook signature (optional for security)
	@Override
	public boolean verifyWebhookSignature(String payload, String signature) {
		//check that implementation is properly configured
        return paystackConfig.verifySignature(payload, signature);
    }

	// Method to process webhook events
	@Override
	public void processWebhookEvent(String payload) {
		// Add logic to process the event and update your database accordingly
		//fixme: check and implement
		System.out.println("Processing Webhook Payload: " + payload);
		// For example: check if it's a "charge.success" event and update payment status
	}
}
