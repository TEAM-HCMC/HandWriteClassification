package ac.kr.inu.service;

import ac.kr.inu.domain.Account;
import ac.kr.inu.repository.AccountRepository;
import ac.kr.inu.util.ShellUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelService {

    private final static String TRAIN = "train/";
    private final static String COMPARE = "compare/";
    private static Logger logger = LoggerFactory.getLogger(ModelService.class);

    private final AccountRepository accountRepository;

    public Map trainModel(Long accountId) {
        String name = getAccountName(accountId);
        String[] callCmd = ShellUtil.getBashCmd("sh ../script/train.sh ", TRAIN + name);
        Map map = ShellUtil.execCommand(callCmd);
        return map;
    }

    public Map compareModel(Long accountId) {
        String name = getAccountName(accountId);
        String[] callCmd = ShellUtil.getBashCmd("sh ../script/compare.sh ", COMPARE + name);
        Map map = ShellUtil.execCommand(callCmd);
        return map;
    }

    public Map watchLearning(Long id) {
        String name = getAccountName(id);

        String[] callCmd = ShellUtil.getBashCmd("sh ../script/러닝이 끝났는지 확인하는 쉘파일.sh ");
        Map map = ShellUtil.execCommand(callCmd);

        return ShellUtil.getFailResult();
    }

    public Map getCompareResult(Long id) {
        String name = getAccountName(id);

        String[] callCmd = ShellUtil.getBashCmd("sh ../script/러닝이 끝났는지 확인하는 쉘파일.sh ");
        Map map = ShellUtil.execCommand(callCmd);

        return ShellUtil.getFailResult();
    }

    private String getAccountName(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(NoSuchElementException::new)
                .getName();
    }
}
