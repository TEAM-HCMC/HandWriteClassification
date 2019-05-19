package ac.kr.inu.service;

import ac.kr.inu.dto.compare.CompareResultResDto;
import ac.kr.inu.repository.AccountRepository;
import ac.kr.inu.util.DirInfo;
import ac.kr.inu.util.S3Uploader;
import ac.kr.inu.util.ShellUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
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
    private final S3Uploader s3Uploader;

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
        uploadComareImageResult(name);
        return map;
    }

    public void uploadComareImageResult(String name) {
        String outputPath = DirInfo.OUTPUT;
        String outputImageReg = name + "([0-9]*)\\..*";
        File dirFile = new File(outputPath);
        Arrays.stream(dirFile.listFiles())
                .filter(file -> file.getName().matches(outputImageReg))
                .forEach(file -> s3Uploader.upload(file,"result/"+name));
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
