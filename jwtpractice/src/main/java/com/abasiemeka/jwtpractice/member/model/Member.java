package com.abasiemeka.jwtpractice.member.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(unique = true)
	private String email;
	private String password;
	private String verificationToken;

	private String role;
	private boolean isEnabled;
	private boolean isCredentialsNonExpired;
	private boolean isNonLocked;
	private boolean isNonExpired;

	@Embedded
	private MemberAccount account;
}
