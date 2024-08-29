package devandagile.customermodule.service;

import devandagile.customermodule.model.dto.PaystackPaymentRequestDTO;
import devandagile.customermodule.model.dto.PaystackPaymentResponseDto;
import devandagile.customermodule.model.dto.VerificationResponse;

public interface PaystackService {

	PaystackPaymentResponseDto initializeTransaction(PaystackPaymentRequestDTO paymentRequest);

	VerificationResponse verifyTransaction(String reference);

	void processWebhook(String payload, String paystackSignature);

	VerificationResponse handleCallback(String reference);
}
