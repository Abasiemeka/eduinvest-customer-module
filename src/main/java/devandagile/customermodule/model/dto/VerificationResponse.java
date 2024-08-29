package devandagile.customermodule.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VerificationResponse(
		boolean status,
		String message,
		VerificationData data
) {
	public record VerificationData(
			long id,
			String domain,
			String status,
			String reference,
			long amount,
			String message,
			@JsonProperty("gateway_response")
			String gatewayResponse,
			String channel,
			String currency,
			@JsonProperty("ip_address")
			String ipAddress,
			String metadata,
			@JsonProperty("log")
			Object transactionLog,
			@JsonProperty("fees")
			Long fees,
			Customer customer,
			@JsonProperty("authorization")
			Object authorization,
			@JsonProperty("plan")
			Object plan
	) {}

	public record Customer(
			long id,
			@JsonProperty("first_name")
			String firstName,
			@JsonProperty("last_name")
			String lastName,
			String email,
			@JsonProperty("customer_code")
			String customerCode,
			String phone,
			String metadata,
			@JsonProperty("risk_action")
			String riskAction
	) {}
}
