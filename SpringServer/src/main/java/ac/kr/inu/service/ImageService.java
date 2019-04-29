package ac.kr.inu.service;

import ac.kr.inu.util.FileSaveUtil;
import ac.kr.inu.util.ShellUtill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageService {

    private static Logger logger = LoggerFactory.getLogger(ImageService.class);


    /**
     * @param file : 입력받은 파일을 originalSource 폴더에 저장합니다.
     * @return : 파일이 저장된 위치를 반환합니다.
     */
    public String saveImage(final MultipartFile file) {
        try {
            final String imageUrl = new FileSaveUtil().upload(file);
            return imageUrl;
        } catch (IOException ioE) {
            logger.error(ioE.getMessage());
            throw new RuntimeException(ioE.getMessage());
        }
    }

    public Map contourImage(String url) {
        String[] callCmd = ShellUtill.getBashCmd("sh ../script/contour.sh ", url);
        Map map = ShellUtill.execCommand(callCmd);
        if (map.size() != 0) {
            return map;
        }
        return null;
    }

    public Map learnModel(String name) {
        String[] callCmd = ShellUtill.getBashCmd("sh ../script/learn.sh ", name);
        Map map = ShellUtill.execCommand(callCmd);
        return map;
    }
}
