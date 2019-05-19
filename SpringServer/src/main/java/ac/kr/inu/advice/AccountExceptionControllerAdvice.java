package ac.kr.inu.advice;

import ac.kr.inu.dto.exception.ExceptionResponseDto;
import ac.kr.inu.exception.NoSuchAccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AccountExceptionControllerAdvice {

    private static final String FIELD = "ACCOUNT";

    @ExceptionHandler(NoSuchAccountException.class)
    public ResponseEntity<ExceptionResponseDto> noAccountExists(NoSuchAccountException e){
        log.info("[NoSuchAccountException] {}",e.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(e.getMessage())
                        .build());
    }

}
