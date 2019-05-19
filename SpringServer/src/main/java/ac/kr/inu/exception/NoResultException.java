package ac.kr.inu.exception;

public class NoResultException extends RuntimeException {
    public NoResultException() {
        super("결과가 존재하지 않습니다.");
    }
}
