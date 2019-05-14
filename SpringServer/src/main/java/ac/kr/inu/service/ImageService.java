package ac.kr.inu.service;

import ac.kr.inu.domain.Account;
import ac.kr.inu.domain.AccountImg;
import ac.kr.inu.repository.AccountImgRepository;
import ac.kr.inu.repository.AccountRepository;
import ac.kr.inu.util.DirInfo;
import ac.kr.inu.util.FileSaveUtil;
import ac.kr.inu.util.ShellUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static ac.kr.inu.util.DirInfo.DEFAULT_SRC_DIR;


@Service
@RequiredArgsConstructor
public class ImageService {

    private static Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final AccountRepository accountRepository;
    private final AccountImgRepository accountImgRepository;

    /**
     * @param file   : 입력받은 파일을 originalSource 폴더에 저장합니다.
     * @param id     : 유저의 고유 아이디값
     * @param subDir : 학습용인지 검증용인지 구분
     * @return : 파일이 저장된 위치를 반환합니다.
     */
    public String saveImage(final MultipartFile file, Long id, String subDir) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        String sub = "";
        if (subDir.equals(DirInfo.TRAIN)) {
            Long subNumber = getImgSubNumber(account);
            sub = "_" + subNumber;
        }
        String name = account.getName() + sub;

        try {
            final String imageUrl = new FileSaveUtil().upload(file, name, subDir);
            AccountImg accountImg = new AccountImg(account);
            accountImgRepository.save(accountImg);
            return imageUrl;
        } catch (IOException ioE) {
            logger.error(ioE.getMessage());
            throw new RuntimeException(ioE.getMessage());
        }
    }

    public Map contourImage(String subDir, Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        String initial = account.getName();
        String trainPath = DEFAULT_SRC_DIR + subDir + initial;

        String[] callCmd = getContourCallCmd(trainPath, subDir);
        Map map = ShellUtil.execCommand(callCmd);
        if (isSuccess(map)) {
            return map;
        }
        return ShellUtil.getFailResult();
    }

    private String[] getContourCallCmd(String path, String subDir) {
        return ShellUtil.getBashCmd("sh ../script/contour.sh ", path, subDir);
    }

    private boolean isSuccess(Map map) {
        return map.size() > 0;
    }

    private Long getImgSubNumber(Account account) {
        List<AccountImg> accountImgs = accountImgRepository.findByAccount(account);
        return accountImgs.stream()
                .mapToLong(AccountImg::getId)
                .max()
                .orElse(0) + 1;
    }

//    public Map contourImage(String url) {
//        String[] callCmd = ShellUtil.getBashCmd("sh ../script/contour.sh ", url);
//        Map map = ShellUtil.execCommand(callCmd);
//        if (isSuccess(map)) {
//            return map;
//        }
//        return ShellUtil.getFailResult();
//    }

}
