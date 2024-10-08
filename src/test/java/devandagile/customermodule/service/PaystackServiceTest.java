//package devandagile.customermodule.service;
//
//import com.github.tomakehurst.wiremock.client.WireMock;
//import com.github.tomakehurst.wiremock.client.WireMockBuilder;
//import devandagile.customermodule.model.dto.PaystackPaymentRequestDTO;
//import devandagile.customermodule.service.serviceImpl.PaystackServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.web.client.RestTemplate;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class PaystackServiceTest {
//
//	@InjectMocks
//	private PaystackServiceImpl paystackService;
//
//	@Mock
//	private RestTemplate restTemplate;
//
//	@BeforeEach
//	public void setUp() {
//		MockitoAnnotations.openMocks(this);
//		WireMock.configureFor("localhost", 8080);
//	}
//
//	@Test
//	public void testInitializePayment() {
//		WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/transaction/initialize"))
//				.willReturn(WireMock.aResponse()
//						.withStatus(200)
//						.withBody("{ \"status\": true, \"data\": { \"authorization_url\": \"https://paystack.com/\" } }")));
//
//		String response = paystackService.initializePayment(
//				new PaystackPaymentRequestDTO("abasiemeka@gmail.com", 8000));
//
//		assertNotNull(response);
//	}
//
//	@Test
//	public void testVerifyPayment() {
//		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/transaction/verify/reference"))
//				.willReturn(WireMock.aResponse()
//						.withStatus(200)
//						.withBody("{ \"status\": true, \"data\": { \"status\": \"success\" } }")));
//
//		boolean response = paystackService.verifyPayment("reference");
//
//		assertTrue(response);
//	}
//}