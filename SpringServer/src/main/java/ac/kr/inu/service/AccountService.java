package ac.kr.inu.service;

import ac.kr.inu.domain.Account;
import ac.kr.inu.dto.account.AccountInfoResDto;
import ac.kr.inu.dto.account.AccountSaveReqDto;
import ac.kr.inu.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public boolean saveAccount(AccountSaveReqDto accountSaveReqDto) {
        checkAlreadyEmail(accountSaveReqDto);

        Account account = accountSaveReqDto.toEntity(bCryptPasswordEncoder);
        accountRepository.save(account);
        return true;
    }

    private void checkAlreadyEmail(AccountSaveReqDto accountSaveReqDto) {
        if (accountRepository.findByEmail(accountSaveReqDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 계정이 존재합니다.");
        }
    }

    public AccountInfoResDto findAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return new AccountInfoResDto(account);
    }
}
