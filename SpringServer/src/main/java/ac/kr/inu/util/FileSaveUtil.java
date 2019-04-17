package ac.kr.inu.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileSaveUtil {


    private static String DEFAULT_SRC_DIR = "../originalSource/";
    private static String DEFAULT_DST_DIR = "../destination/";

    public String upload(MultipartFile uploadFile) throws IOException {
        String origName = uploadFile.getOriginalFilename();
        String srcUrl;
        try {

            DirUtil.mkDir(DEFAULT_SRC_DIR);
            DirUtil.mkDir(DEFAULT_DST_DIR);

            srcUrl = DEFAULT_SRC_DIR + origName;

            byte[] data = uploadFile.getBytes();
            FileOutputStream fos = new FileOutputStream(srcUrl);
            fos.write(data);
            fos.close();

        } catch (StringIndexOutOfBoundsException e) {
            //파일이 없을 경우 예외 처리
            srcUrl = null;
        }
        return srcUrl;
    }

}
