package devandagile.customermodule.service.serviceImpl;

import devandagile.customermodule.service.PaystackService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaystackServiceImpl implements PaystackService {

	@Value("${paystack.secret.key}")
	private String paystackSecretKey;

	private final RestTemplate restTemplate;

	public PaystackServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public String initializePayment(Object paymentRequest) {
		String url = "https://api.paystack.co/transaction/initialize";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + paystackSecretKey);
		headers.set("Content-Type", "application/json");

		HttpEntity<Object> entity = new HttpEntity<>(paymentRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		return response.getBody();
	}

	@Override
	public String verifyPayment(String reference) {
		String url = "https://api.paystack.co/transaction/verify/" + reference;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + paystackSecretKey);

		HttpEntity<Object> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		return response.getBody();
	}
}
