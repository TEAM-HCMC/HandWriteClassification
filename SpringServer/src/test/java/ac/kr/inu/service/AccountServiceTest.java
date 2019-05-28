package ac.kr.inu.service;

import ac.kr.inu.domain.Account;
import ac.kr.inu.dto.account.LogResDto;
import ac.kr.inu.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@TestPropertySource("classpath:aws.properties")
@ActiveProfiles({"dev"})
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AccountServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Test
    public void 사용자_학습정보_가져오기() {
        Account account = accountRepository.findAll().get(0);

        LogResDto logResDto = accountService.getLog(account);
    }
}

