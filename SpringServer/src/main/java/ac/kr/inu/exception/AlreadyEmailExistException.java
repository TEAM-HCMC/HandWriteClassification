package ac.kr.inu.exception;

public class AlreadyEmailExistException extends RuntimeException {

    public AlreadyEmailExistException() {
        super("중복된 이메일이 존재합니다.");
    }
}
