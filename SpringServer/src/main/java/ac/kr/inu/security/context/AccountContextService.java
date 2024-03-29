package ac.kr.inu.security.context;

import ac.kr.inu.domain.Account;
import ac.kr.inu.exception.NoSuchAccountException;
import ac.kr.inu.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * UserDetailsService와 같은 역할을 한다고 생각하면 된다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AccountContextService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(NoSuchAccountException::new);
        return account.toAccountContext();
    }

}
