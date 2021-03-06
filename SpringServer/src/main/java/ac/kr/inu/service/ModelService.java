package ac.kr.inu.service;

import ac.kr.inu.domain.Account;
import ac.kr.inu.domain.ComparedImg;
import ac.kr.inu.dto.compare.CompareResultResDto;
import ac.kr.inu.exception.NoSuchAccountException;
import ac.kr.inu.repository.AccountRepository;
import ac.kr.inu.repository.ComparedImgRepository;
import ac.kr.inu.util.DirInfo;
import ac.kr.inu.util.S3Uploader;
import ac.kr.inu.util.ShellUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;

import static ac.kr.inu.service.ShellInfo.*;
import static ac.kr.inu.util.DirInfo.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelService {

    private static final String UPLOAD_S3_REG = "([0-9]*_[0-9]*)\\..*";

    private final AccountRepository accountRepository;
    private final ComparedImgRepository comparedImgRepository;
    private final S3Uploader s3Uploader;

    public Map trainModel(Long accountId) {
        String name = getAccountName(accountId);
        String[] callCmd = ShellUtil.getBashCmd(TRAIN_SHELL, TRAIN + name);
        Map map = ShellUtil.execCommand(callCmd);
        return map;
    }

    @Transactional
    public Map compareModel(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(NoSuchAccountException::new);
        String name = account.getModelName();

        String[] callCmd = ShellUtil.getBashCmd(COMPARE_SHELL, COMPARE + name);
        Map map = ShellUtil.execCommand(callCmd);

        removeAlreadyComparedImages(account);
        uploadCompareImageResult(account);

        return map;
    }

    @Transactional
    public void removeAlreadyComparedImages(Account account) {
        comparedImgRepository.deleteAllByAccount(account);
    }

    @Transactional
    public void uploadCompareImageResult(Account account) {
        String outputPath = DirInfo.OUTPUT;
        String name = account.getModelName();
        String outputImageReg = name + UPLOAD_S3_REG;

        File dirFile = new File(outputPath);

        Arrays.stream(dirFile.listFiles())
                .filter(file -> file.getName().matches(outputImageReg))
                .forEach(file -> saveFile(file, account, name));
    }

    private void saveFile(File file, Account account, String name) {
        String url = s3Uploader.upload(file, S3_RESULT + name);
        String percentage = getPercentage(file, 2);
        if (isZero(file)) {
            percentage = getPercentage(file, 1);
        }
        saveOnDatabase(url, percentage, account);
    }

    private boolean isZero(File file) {
        return file.getName().split("_")[1].substring(0, 1).equals("0");
    }

    private String getPercentage(File file, int length) {
        return file.getName().split("_")[1].substring(0, length);
    }

    private void saveOnDatabase(String url, String percentage, Account account) {
        comparedImgRepository.save(new ComparedImg(url, percentage, account));
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
                .getModelName();
    }
}
