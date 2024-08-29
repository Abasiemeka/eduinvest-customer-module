package devandagile.customermodule.service;

import devandagile.customermodule.model.dto.SignupDTO;
import devandagile.customermodule.model.entity.Customer;
import jakarta.validation.constraints.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CustomerService extends UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	Customer getCustomerById(Long id);

	Customer getCustomerByEmailOrNull(Email email);

	ResponseEntity<String> signup(SignupDTO customer, String encodedPassword);

	ResponseEntity<String> verifyCustomerEmail(String vtoken);

	boolean customerExists(Email email);
}
