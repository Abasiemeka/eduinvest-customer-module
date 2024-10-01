package devandagile.customermodule.model.entity;

import devandagile.customermodule.model.enums.Authority;
import devandagile.customermodule.model.enums.MethodFor2FA;
import devandagile.customermodule.model.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_account")
public class UserAccount implements UserDetails{
	@NonNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder.Default
	@Column(nullable = false)
	private boolean isEnabled = false;

	@Builder.Default
	@Column(nullable = false)
	private boolean isLoggedIn = false;

	@Builder.Default
	@Column(nullable = false)
	private boolean enable2FA = false;

	@Enumerated
	private MethodFor2FA methodFor2FA;

	@NonNull
	@Email
	private String username;

	@Enumerated
	@NonNull
	private Roles role;

	@NonNull
	@Singular
	@Enumerated(value = EnumType.STRING)
	@ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_account_authority",
			joinColumns = @JoinColumn(name = "user_account_id", referencedColumnName = "id"),
			foreignKey = @ForeignKey(name = "user_account_authority_fk"))
	private Set<Authority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return "";
	}

	@Override
	public boolean isAccountNonExpired() {
		return UserDetails.super.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return UserDetails.super.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}

	public void setProviderId(String sub) {
	}
}
