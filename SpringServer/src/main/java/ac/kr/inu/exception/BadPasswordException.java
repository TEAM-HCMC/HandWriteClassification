package ac.kr.inu.exception;

public class BadPasswordException extends RuntimeException{
    public BadPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
