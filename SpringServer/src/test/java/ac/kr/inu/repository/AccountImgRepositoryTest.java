package ac.kr.inu.repository;

import ac.kr.inu.domain.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@SpringBootTest
@TestPropertySource("classpath:aws.properties")
@ActiveProfiles({"dev"})
@RunWith(SpringRunner.class)
public class AccountImgRepositoryTest {

    @Autowired
    AccountImgRepository accountImgRepository;

    @Autowired
    AccountRepository accountRepository;

    Account pci;
    Account cip;

    @Before
    public void setUp() throws Exception {
        pci = accountRepository.findByEmail("pci2676")
                .orElseThrow(NoSuchElementException::new);
        cip = accountRepository.findByEmail("cip0508")
                .orElseThrow(NoSuchElementException::new);
    }

    @Test
    public void 계정찾기() {
        List<String> emails = accountRepository.findAll().stream()
                .map(Account::getEmail)
                .collect(Collectors.toList());
    }
}