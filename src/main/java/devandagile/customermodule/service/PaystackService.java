package devandagile.customermodule.service;

import devandagile.customermodule.model.dto.PaystackPaymentRequestDTO;

public interface PaystackService {

	void processWebhookEvent(String payload);

	String initializePayment(PaystackPaymentRequestDTO paymentRequest);

	boolean verifyPayment(String reference);

	boolean verifyWebhookSignature(String payload, String signature);
}
