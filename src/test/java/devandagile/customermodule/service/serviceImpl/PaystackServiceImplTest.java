package devandagile.customermodule.service.serviceImpl;// Test file path: C:/Users/abasi/devandagile/Projects/eduinvest/src/test/java/devandagile/customermodule/service/serviceImpl/PaystackServiceImpl.java

import com.fasterxml.jackson.databind.ObjectMapper;
import devandagile.customermodule.model.dto.VerificationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaystackServiceImplTest {

    private PaystackServiceImpl paystackService;
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        ObjectMapper objectMapper = new ObjectMapper();
        paystackService = new PaystackServiceImpl(restTemplate, "sk_test_abcdef1234567890", "https://api.paystack.co", "/transaction/verify/", objectMapper);
    }

    @Test
    public void shouldReturnFalseWhenReferenceIsNotFound() {
        // Given
        String reference = "not_found_reference";
        ResponseEntity<String> responseEntity
                = new ResponseEntity<>("{\"status\": false}", HttpStatus.OK);
        when
                (restTemplate
                        .exchange(eq("https://api.paystack.co/transaction/verify/" + reference),
                                eq(HttpMethod.GET),
                                any(),
                                eq(String.class))).thenReturn(responseEntity);

        // When
        VerificationResponse result = paystackService.verifyTransaction(reference);

        // Then
        assertFalse(result.status());
    }
}