package ac.kr.inu.util;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;

import static ac.kr.inu.util.DirInfo.*;

public class FileSaveUtil {

    private static Logger log = LoggerFactory.getLogger(FileSaveUtil.class);

    /**
     * @param uploadFile 업로드 받은 파일
     * @param name       사용자가 입력한 이니셜
     * @param subDir     train/ or compare/
     * @return
     * @throws IOException
     */
    public String upload(MultipartFile uploadFile, String name, String subDir) throws IOException {
        String origName = uploadFile.getOriginalFilename();
        String ext = FilenameUtils.getExtension(origName);

        String srcUrl;
        try {

            DirUtil.mkDir(DEFAULT_SRC_DIR + subDir);
            DirUtil.mkDir(DEFAULT_DST_DIR);

            srcUrl = DEFAULT_SRC_DIR + subDir + name + DOT + ext;

            byte[] data = uploadFile.getBytes();
            FileOutputStream fos = new FileOutputStream(srcUrl);
            fos.write(data);
            fos.close();

            srcUrl = subDir + name;

        } catch (StringIndexOutOfBoundsException e) {
            //파일이 없을 경우 예외 처리
            srcUrl = null;
        }
        return srcUrl;
    }

}
