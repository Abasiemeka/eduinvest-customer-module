package devandagile.customermodule.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import devandagile.customermodule.model.dto.GenericResponse;
import devandagile.customermodule.model.dto.PaystackPaymentRequestDTO;
import devandagile.customermodule.model.dto.PaystackPaymentResponseDto;
import devandagile.customermodule.model.dto.VerificationResponse;
import devandagile.customermodule.service.PaystackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@Slf4j
public class PaystackServiceImpl implements PaystackService {

	private final RestTemplate restTemplate;
	private final String paystackSecretKey;
	private final String paystackInitializeUrl;
	private final String paystackVerifyUrl;
	private final ObjectMapper objectMapper;

	public PaystackServiceImpl(RestTemplate restTemplate,
	                           @Value("${paystack.secretKey}") String paystackSecretKey,
	                           @Value("${paystack.initializeUrl}") String paystackInitializeUrl,
	                           @Value("${paystack.verifyUrl}") String paystackVerifyUrl,
	                           ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.paystackSecretKey = paystackSecretKey;
		this.paystackInitializeUrl = paystackInitializeUrl;
		this.paystackVerifyUrl = paystackVerifyUrl;
		this.objectMapper = objectMapper;
	}

	@Retryable(retryFor = {RestClientException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
	@Override
	public PaystackPaymentResponseDto initializeTransaction(PaystackPaymentRequestDTO paymentRequest) {
		validatePaymentRequest(paymentRequest);

		HttpHeaders headers = createHeaders();
		HttpEntity<PaystackPaymentRequestDTO> request = new HttpEntity<>(paymentRequest, headers);

		ResponseEntity<GenericResponse> response = restTemplate.postForEntity(paystackInitializeUrl, request, GenericResponse.class);
		log.info("Payment initialized successfully for email: {}", paymentRequest.email());
		return objectMapper.convertValue(Objects.requireNonNull(response.getBody()).data(), PaystackPaymentResponseDto.class);
	}

	@Retryable(retryFor = {RestClientException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
	@Override
	public VerificationResponse verifyTransaction(String reference) {
		HttpHeaders headers = createHeaders();
		HttpEntity<?> entity = new HttpEntity<>(headers);

		String url = paystackVerifyUrl + reference;
		ResponseEntity<GenericResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, GenericResponse.class);
		log.info("Payment verified for reference: {}", reference);
		return objectMapper.convertValue(Objects.requireNonNull(response.getBody()).data(), VerificationResponse.class);
	}

	@Override
	public void processWebhook(String payload, String paystackSignature) {
		if (!isValidWebhookSignature(payload, paystackSignature)) {
			throw new SecurityException("Invalid webhook signature");
		}

		log.info("Processing webhook payload: {}", payload);
		// Process the webhook payload
		// Update payment status in the database
		// Consider other necessary business logic (e.g., product activation)
	}

	@Override
	public VerificationResponse handleCallback(String reference) {
		log.info("Processing callback for reference: {}", reference);
		VerificationResponse verificationResponse = verifyTransaction(reference);

		if (verificationResponse.status() && "success".equalsIgnoreCase(verificationResponse.data().status())) {
			log.info("Payment successful for reference: {}", reference);
			// Update order status in the database
			// Send confirmation email to customer
			// Consider other necessary business logic
		} else {
			log.warn("Payment failed for reference: {}", reference);
			// Handle failed payment (e.g., update order status, notify customer)
		}

		return verificationResponse;
	}

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + paystackSecretKey);
		return headers;
	}

	private void validatePaymentRequest(PaystackPaymentRequestDTO paymentRequest) {
		if (paymentRequest.amount() <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}
		if (!StringUtils.hasText(paymentRequest.email())) {
			throw new IllegalArgumentException("Email is required");
		}
	}

	private boolean isValidWebhookSignature(String payload, String paystackSignature) throws AuthenticationException {
		try {
			Mac sha512Hmac = Mac.getInstance("HmacSHA512");
			SecretKeySpec secretKeySpec = new SecretKeySpec(paystackSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
			sha512Hmac.init(secretKeySpec);
			byte[] macData = sha512Hmac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
			String result = bytesToHex(macData);
			return result.equals(paystackSignature);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			log.error("Error validating webhook signature", e);
			return false;
		}
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes) {
			result.append(String.format("%02x", b));
		}
		return result.toString();
	}
}









//@Service
//public class PaystackServiceImpl implements PaystackService {
//
//	private final String PAYSTACK_SECRET_KEY;
//	private final PaystackConfig paystackConfig;
//	private final RestTemplate restTemplate;
//
//	public PaystackServiceImpl(@Value("${paystack.secret.key}") String paystackSecretKey, PaystackConfig paystackConfig, RestTemplate restTemplate) {
//		PAYSTACK_SECRET_KEY = paystackSecretKey;
//		this.paystackConfig = paystackConfig;
//		this.restTemplate = restTemplate;
//	}
//
//	@Override
//	public @ResponseBody String initializePayment(PaystackPaymentRequestDTO paymentRequestInKobo) {
//		//todo: each call to initialize payment should create a transaction object which will be updated
//		// in the database by the webhook (primarily) and by the verify method (secondary, synchronized with the webhook)
//		// based on the transaction status
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);
//		headers.set("Content-Type", "application/json");
//
//		HttpEntity<Object> requestEntity = new HttpEntity<>(paymentRequestInKobo, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//
////		@ResponseBody String responseBody = response.getBody();
//
//		return response.getBody();
//	}
//
//	public boolean verifyPayment(String reference) {
//		String url = "https://api.paystack.co/transaction/verify/" + reference;
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);
//
//		HttpEntity<String> entity = new HttpEntity<>(headers);
//		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//
//		// Logic to parse the response and verify the status
//		return Objects.requireNonNull(response.getBody()).contains("\"status\": true");
//
//		// todo: implement the transaction status update logic here, but secondary to the one in the webhook
//		//  For demonstration purposes, let's assume the transaction is successful, then set transaction.status to SUCCESSFUL
//	}
//
//	// Method to verify webhook signature (optional for security)
//	@Override
//	public boolean verifyWebhookSignature(String payload, String signature) {
//		//check that implementation is properly configured
//        return paystackConfig.verifySignature(payload, signature);
//    }
//
//	// Method to process webhook events
//	@Override
//	public void processWebhookEvent(String payload) {
//		// fixme: check and implement
//		// Add logic to process the event and update your database accordingly
//		// that is, update the transaction object in the database based on the event
//		//This is the primary actor for doing so.
//		System.out.println("Processing Webhook Payload: " + payload);
//		// For example: check if it's a "charge.success" event and update payment status
//	}
//}
