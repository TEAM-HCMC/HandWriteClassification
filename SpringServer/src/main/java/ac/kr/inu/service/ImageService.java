package ac.kr.inu.service;

import ac.kr.inu.config.CommonConfig;
import ac.kr.inu.domain.Account;
import ac.kr.inu.domain.AccountImg;
import ac.kr.inu.domain.ComparedImg;
import ac.kr.inu.dto.compare.ImgUrlResDto;
import ac.kr.inu.exception.FileNotSaveException;
import ac.kr.inu.exception.NoSuchAccountException;
import ac.kr.inu.repository.AccountImgRepository;
import ac.kr.inu.repository.AccountRepository;
import ac.kr.inu.repository.ComparedImgRepository;
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
import java.util.stream.Collectors;

import static ac.kr.inu.service.ShellInfo.CONTOUR_SHELL;
import static ac.kr.inu.util.DirInfo.DEFAULT_SRC_DIR;
import static ac.kr.inu.util.DirInfo.TRAIN;


@Service
@RequiredArgsConstructor
public class ImageService {

    private static Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final AccountRepository accountRepository;
    private final AccountImgRepository accountImgRepository;
    private final ComparedImgRepository comparedImgRepository;

    /**
     * @param file   : 입력받은 파일을 originalSource 폴더에 저장합니다.
     * @param id     : 유저의 고유 아이디값
     * @param subDir : 학습용인지 검증용인지 구분
     * @return : 파일이 저장된 위치를 반환합니다.
     */

    public String saveImage(final MultipartFile file, Long id, String subDir) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NoSuchAccountException::new);

        String originalName = file.getOriginalFilename();
        AccountImg accountImg = new AccountImg(account, originalName);
        accountImgRepository.save(accountImg);

        String sub = getSubNumber(subDir, accountImg);

        String subName = account.getModelName() + sub;

        try {
            final String imageUrl = new FileSaveUtil().upload(file, subName, subDir);
            return imageUrl;
        } catch (IOException ioE) {
            logger.error(ioE.getMessage());
            throw new FileNotSaveException();
        }
    }

    public Map contourImage(String subDir, Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NoSuchAccountException::new);
        String initial = account.getModelName();
        String trainPath = DEFAULT_SRC_DIR + subDir + initial;

        String[] callCmd = getContourCallCmd(trainPath, subDir);
        Map map = ShellUtil.execCommand(callCmd);
        if (isSuccess(map)) {
            return map;
        }
        return ShellUtil.getFailResult();
    }

    public List<ImgUrlResDto> getComparedImgsUrl(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NoSuchAccountException::new);
        List<ComparedImg> comparedImgs = CommonConfig.getCheckedList(comparedImgRepository.findByAccount(account));
        return comparedImgs.stream()
                .map(img -> new ImgUrlResDto(img))
                .collect(Collectors.toList());
    }

    public List<String> getFileNames(Long id) {
        List<AccountImg> accountImgs = accountImgRepository.findByAccount_Id(id);
        CommonConfig.getCheckedList(accountImgs);
        return accountImgs.stream()
                .map(AccountImg::getOriginalName)
                .collect(Collectors.toList());
    }

    public void deleteFile(String subName) {
        Long imgIdx = Long.parseLong(subName.split("_")[0]);
        accountImgRepository.deleteById(imgIdx);
        //TODO
        //shell로 해당 서브파일 삭제
    }

    public void deleteAllFile(Long id) {
        accountImgRepository.deleteAllByAccount_Id(id);
        //TODO
        //shell로 해당 서브파일 전부 삭제
    }

    private String getSubNumber(String subDir, AccountImg accountImg) {
        if (subDir.equals(TRAIN)) {
            Long subNumber = accountImg.getId();
            return "_" + subNumber;
        }
        return "";
    }

    private String[] getContourCallCmd(String path, String subDir) {
        return ShellUtil.getBashCmd(CONTOUR_SHELL, path, subDir);
    }

    private boolean isSuccess(Map map) {
        return map.size() > 0;
    }


}
