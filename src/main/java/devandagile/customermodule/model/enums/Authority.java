package devandagile.customermodule.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
	ROLE_USER,
	ROLE_AGENT,
	ROLE_SUB_ADMIN,
	ROLE_ADMIN;

	@Override
	public String getAuthority() {
		return name();
	}
}
