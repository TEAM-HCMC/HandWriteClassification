package ac.kr.inu.exception;

public class NoShellExistsException extends RuntimeException {
    public NoShellExistsException() {
        super("해당 Shell 스크립트가 존재하지 않습니다.");
    }
}
