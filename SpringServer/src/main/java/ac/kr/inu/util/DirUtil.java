package ac.kr.inu.util;

import java.io.File;

public class DirUtil {

    /**
     * @param src : 인자로 받은 파라미터로 하위 디렉토리에 새로운 디렉토리를 생성합니다.
     */
    public static void mkDir(String src) {
        String subDir = src;
        File file = new File(subDir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
