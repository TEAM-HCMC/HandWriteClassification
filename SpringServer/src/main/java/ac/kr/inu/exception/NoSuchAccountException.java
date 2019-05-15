package ac.kr.inu.exception;

import java.util.NoSuchElementException;

public class NoSuchAccountException extends NoSuchElementException {
    public NoSuchAccountException() {
        super("계정이 존재하지 않습니다.");
    }
}
