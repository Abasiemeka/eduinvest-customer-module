package devandagile.customermodule.controller;

import devandagile.customermodule.model.dto.GenericResponse;
import devandagile.customermodule.model.dto.PaystackPaymentRequestDTO;
import devandagile.customermodule.model.dto.PaystackPaymentResponseDto;
import devandagile.customermodule.model.dto.VerificationResponse;
import devandagile.customermodule.service.PaystackService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {

	private final PaystackService paystackService;

	@Autowired
	public PaymentController(PaystackService paystackService) {
		this.paystackService = paystackService;
	}

	@PostMapping("/initialize")
	public GenericResponse initializePayment(@Valid @RequestBody PaystackPaymentRequestDTO paymentRequest) {
		PaystackPaymentResponseDto response = paystackService.initializeTransaction(paymentRequest);
		return GenericResponse.successful("Payment initialized successfully", response);
	}

	@GetMapping("/verify/{reference}")
	public GenericResponse verifyPayment(@PathVariable String reference) {
		VerificationResponse response = paystackService.verifyTransaction(reference);
		return GenericResponse.successful("Payment verified", response);
	}

	@PostMapping("/webhook")
	public GenericResponse handleWebhook(@RequestBody String payload, @RequestHeader("X-Paystack-Signature") String paystackSignature) {
		paystackService.processWebhook(payload, paystackSignature);
		return GenericResponse.successful("Webhook processed successfully");
	}

	@GetMapping("/callback")
	public GenericResponse handleCallback(@RequestParam String reference) {
		VerificationResponse response = paystackService.handleCallback(reference);
		return GenericResponse.successful("Payment callback processed", response);
	}
}










//package devandagile.customermodule.controller;
//
//import devandagile.customermodule.model.dto.PaystackPaymentRequestDTO;
//import devandagile.customermodule.service.PaystackService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/payments")
//@Slf4j
//public class PaymentController {
//
//	private final PaystackService paystackService;
//
//	@Autowired
//	public PaymentController(PaystackService paystackService) {
//		this.paystackService = paystackService;
//	}
//
//	@PostMapping("/initialize")
//	public ResponseEntity<String> initializePayment(@RequestParam String email, int amount) {
//		PaystackPaymentRequestDTO paymentRequestInKobo = PaystackPaymentRequestDTO
//				.builder()
//				.email(email)
//				.amount(amount * 100)   //Converts amount to Kobo as Paystack expects
//				.build();
//		String response = paystackService.initializePayment(paymentRequestInKobo);
//		return ResponseEntity.ok(response);
//	}
//
//	@GetMapping("/verify")
//	public ResponseEntity<Boolean> verifyPayment(@RequestParam String reference) {
//		boolean response = paystackService.verifyPayment(reference);
//		return ResponseEntity.ok(response);
//	}
//
//	// Handle Paystack payment callback
//	@GetMapping("/callback")
//	public ResponseEntity<String> handleCallback(@RequestParam("reference") String reference) {
//		boolean isPaymentSuccessful = paystackService.verifyPayment(reference);
//
//		if (isPaymentSuccessful) {
//			return ResponseEntity.ok("Payment successful! Reference: " + reference);
//		} else {
//			return ResponseEntity.badRequest().body("Payment verification failed.");
//		}
//	}
//
//	// Handle Paystack Webhook events
//	@PostMapping("/webhook")
//	public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("x-paystack-signature") String signature) {
//		if (!paystackService.verifyWebhookSignature(payload, signature)) {
//			return ResponseEntity.status(401).body("Invalid signature");
//		}
//
//		paystackService.processWebhookEvent(payload);
//		return ResponseEntity.ok("Webhook event processed successfully");
//	}
//}
