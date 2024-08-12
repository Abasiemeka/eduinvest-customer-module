package devandagile.customermodule.service.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService extends BCryptPasswordEncoder {
}
