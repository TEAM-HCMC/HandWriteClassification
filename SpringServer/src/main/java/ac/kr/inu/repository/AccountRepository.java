package ac.kr.inu.repository;

import ac.kr.inu.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(final String email);
    Optional<Account> findById(final Long id);
}
