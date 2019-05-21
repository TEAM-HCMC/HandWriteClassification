package ac.kr.inu.advice;

import ac.kr.inu.dto.exception.ExceptionResponseDto;
import ac.kr.inu.exception.AlreadyEmailExistException;
import ac.kr.inu.exception.AlreadyNameExistException;
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(AlreadyEmailExistException.class)
    public ResponseEntity<ExceptionResponseDto> alreadyEmailExists(AlreadyEmailExistException e){
        log.info("[AlreadyEmailExistException] {}",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(AlreadyNameExistException.class)
    public ResponseEntity<ExceptionResponseDto> alreadyNameExists(AlreadyNameExistException e){
        log.info("[AlreadyNameExistException] {}",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(e.getMessage())
                        .build());
    }

}
