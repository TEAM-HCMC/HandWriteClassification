package ac.kr.inu.advice;

import ac.kr.inu.dto.exception.ExceptionResponseDto;
import ac.kr.inu.exception.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ShellExceptionControllerAdvice {

    private static final String FIELD = "SHELL";

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ExceptionResponseDto> noResultExists(NoResultException e) {
        log.info("[NoResultException] {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(e.getMessage())
                        .build());
    }
}
