package ac.kr.inu.service;

import ac.kr.inu.domain.Account;
import ac.kr.inu.domain.Model;
import ac.kr.inu.dto.account.*;
import ac.kr.inu.exception.AlreadyEmailExistException;
import ac.kr.inu.exception.AlreadyNameExistException;
import ac.kr.inu.repository.AccountRepository;
import ac.kr.inu.repository.ModelRepository;
import ac.kr.inu.util.DirInfo;
import ac.kr.inu.util.FileReadUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final String TRAIN = "train";
    private static final String CONTOUR = "contour";

    private final AccountRepository accountRepository;
    private final ModelRepository modelRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public boolean saveAccount(AccountSaveReqDto saveReqDto) {
        checkAlreadyEmail(saveReqDto);
        checkAlreadyName(saveReqDto);
        Model model = saveReqDto.toModelEntity();
        Account account = saveReqDto.toAccountEntity(bCryptPasswordEncoder, model);
        modelRepository.save(model);
        accountRepository.save(account);
        return true;
    }

    private void checkAlreadyName(AccountSaveReqDto saveReqDto) {
        if (modelRepository.findByName(saveReqDto.getName()).isPresent()) {
            throw new AlreadyNameExistException();
        }
    }

    private void checkAlreadyEmail(AccountSaveReqDto saveReqDto) {
        if (accountRepository.findByEmail(saveReqDto.getEmail()).isPresent()) {
            throw new AlreadyEmailExistException();
        }
    }

    public AccountInfoResDto getAccountInfo(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        LogResDto logResDto = getLog(account);

        return new AccountInfoResDto(account, logResDto);
    }

    public LogResDto getLog(Account account) {
        String LogDir = DirInfo.LOG + account.getModelName();
        File[] files = new File(LogDir).listFiles();
        FileReadUtils fileReadUtils = new FileReadUtils();

        TrainLogResDto trainLogResDto = new TrainLogResDto(fileReadUtils.findCategoryLogFile(files, TRAIN));
        ContourLogResDto contourLogResDto = new ContourLogResDto(fileReadUtils.findCategoryLogFile(files, CONTOUR));

        return new LogResDto(trainLogResDto, contourLogResDto);
    }

}
