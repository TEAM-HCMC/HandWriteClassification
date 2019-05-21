package ac.kr.inu.exception;

public class AlreadyNameExistException extends RuntimeException {

    public AlreadyNameExistException() {
        super("중복된 모델이름이 존재합니다.");
    }
}
