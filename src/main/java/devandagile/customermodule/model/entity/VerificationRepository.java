package devandagile.customermodule.model.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
	@NonNull
	Optional<Verification> findByEmailIgnoreCase(@NonNull String email);
}