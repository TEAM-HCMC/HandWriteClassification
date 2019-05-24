package ac.kr.inu.service;

import ac.kr.inu.domain.Account;
import ac.kr.inu.domain.Model;
import ac.kr.inu.dto.account.AccountInfoResDto;
import ac.kr.inu.dto.account.AccountSaveReqDto;
import ac.kr.inu.exception.AlreadyEmailExistException;
import ac.kr.inu.exception.AlreadyNameExistException;
import ac.kr.inu.repository.AccountRepository;
import ac.kr.inu.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelRepository modelRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public boolean saveAccount(AccountSaveReqDto accountSaveReqDto) {
        checkAlreadyEmail(accountSaveReqDto);
        checkAlreadyName(accountSaveReqDto);
        Model model = accountSaveReqDto.toModelEntity();
        Account account = accountSaveReqDto.toAccountEntity(bCryptPasswordEncoder, model);
        modelRepository.save(model);
        accountRepository.save(account);
        return true;
    }

    private void checkAlreadyName(AccountSaveReqDto accountSaveReqDto) {
        if (modelRepository.findByName(accountSaveReqDto.getName()).isPresent()) {
            throw new AlreadyNameExistException();
        }
    }

    private void checkAlreadyEmail(AccountSaveReqDto accountSaveReqDto) {
        if (accountRepository.findByEmail(accountSaveReqDto.getEmail()).isPresent()) {
            throw new AlreadyEmailExistException();
        }
    }

    public AccountInfoResDto findAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return new AccountInfoResDto(account);
    }
}
