package ac.kr.inu.service;

import ac.kr.inu.domain.Account;
import ac.kr.inu.domain.AccountImg;
import ac.kr.inu.repository.AccountImgRepository;
import ac.kr.inu.repository.AccountRepository;
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


@Service
@RequiredArgsConstructor
public class ImageService {

    private static Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final AccountRepository accountRepository;
    private final AccountImgRepository accountImgRepository;

    /**
     * @param file : 입력받은 파일을 originalSource 폴더에 저장합니다.
     * @param id : 유저의 고유 아이디값
     * @param subDir : 학습용인지 검증용인지 구분
     * @return : 파일이 저장된 위치를 반환합니다.
     */
    public String saveImage(final MultipartFile file, Long id, String subDir) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        Long subNumber = getImgSubNumber(account);

        String name = account.getName() + "_" + subNumber;

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

    public Map contourImage(String url) {
        String[] callCmd = ShellUtil.getBashCmd("sh ../script/contour.sh ", url);
        Map map = ShellUtil.execCommand(callCmd);
        if (map.size() != 0) {
            return map;
        }
        return null;
    }

    private Long getImgSubNumber(Account account) {
        List<AccountImg> accountImgs = accountImgRepository.findByAccount(account);
        return accountImgs.stream()
                .mapToLong(AccountImg::getId)
                .max()
                .orElse(1) + 1;
    }

}
