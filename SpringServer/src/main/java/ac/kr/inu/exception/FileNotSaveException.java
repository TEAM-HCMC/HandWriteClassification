package ac.kr.inu.exception;

public class FileNotSaveException extends RuntimeException {
    public FileNotSaveException() {
        super("파일 업로드를 실패하였습니다.");
    }
}
