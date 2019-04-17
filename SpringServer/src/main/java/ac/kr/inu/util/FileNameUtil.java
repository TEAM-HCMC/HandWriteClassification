package ac.kr.inu.util;

public class FileNameUtil {


    /**
     *
     * @param src : 원본 파일의 경로
     * @return : 원본파일에서 경로를 제외한 파일명을 반환
     */
    public static String getSourceName(String src){
        //경로 제외 원본 파일명
        int fileIndex = src.split("/").length - 1;
        String originalWithExt = src.split("/")[fileIndex];
        return originalWithExt;
    }

    /**
     *
     * @param src 원본 파일의 경로
     * @return : 원본 파일에서 경로와 확장자를 제외한 파일명을 반환
     */
    public static String getSourceNameWithOutExt(String src) {
        String originalWithExt = getSourceName(src);
        //확장자 제외 원본 파일명
        String pureName = originalWithExt.substring(0, originalWithExt.indexOf('.'));
        return pureName;
    }
}
