package ac.kr.inu.service;

import ac.kr.inu.dto.compare.CompareResultResDto;
import ac.kr.inu.repository.AccountRepository;
import ac.kr.inu.util.ShellUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;

import static ac.kr.inu.service.ShellInfo.*;
import static ac.kr.inu.util.DirInfo.COMPARE;
import static ac.kr.inu.util.DirInfo.TRAIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelService {

    private final AccountRepository accountRepository;

    public Map trainModel(Long accountId) {
        String name = getAccountName(accountId);
        String[] callCmd = ShellUtil.getBashCmd(TRAIN_SHELL, TRAIN + name);
        Map map = ShellUtil.execCommand(callCmd);
        return map;
    }

    public Map compareModel(Long accountId) {
        String name = getAccountName(accountId);
        String[] callCmd = ShellUtil.getBashCmd(COMPARE_SHELL, COMPARE + name);
        Map map = ShellUtil.execCommand(callCmd);
        return map;
    }

    public Map watchTraining(Long id) {
        String name = getAccountName(id);

        String[] callCmd = ShellUtil.getBashCmd(TRAIN_WATCH_SHELL, name);
        Map map = ShellUtil.execCommand(callCmd);

        return ShellUtil.getFailResult();
    }

    public CompareResultResDto getCompareResult(Long id) {
        String name = getAccountName(id);

        String[] callCmd = ShellUtil.getBashCmd(COMPARE_RESULT_SHELL, name);
        Map map = ShellUtil.execCommand(callCmd);
        CompareResultResDto resDto = new CompareResultResDto(map);

        return resDto;
    }

    private String getAccountName(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(NoSuchElementException::new)
                .getName();
    }
}
