package devandagile.customermodule.service;

import devandagile.customermodule.model.dto.GenericResponse;
import devandagile.customermodule.model.dto.SignupDTO;
import devandagile.customermodule.model.entity.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CustomerService extends UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	Customer getCustomerById(Long id);

	Customer getCustomerByEmailOrNull(String email);

	GenericResponse signup(SignupDTO customer, String encodedPassword);

	GenericResponse verifyCustomerEmail(String vtoken);

	GenericResponse login(String email, String password);

	boolean customerExists(String email);
}
