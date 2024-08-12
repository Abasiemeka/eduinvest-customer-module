package devandagile.customermodule.service;

public interface PaystackService {
	String initializePayment(Object paymentRequest);

	String verifyPayment(String reference);
}
