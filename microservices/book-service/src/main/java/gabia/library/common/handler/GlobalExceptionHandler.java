package gabia.library.common.handler;

import gabia.library.common.exception.response.ErrorResponse;
import gabia.library.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

import static gabia.library.common.exception.message.CommonExceptionMessage.*;

// TODO: 추후에 공통으로 뺄 코드

/**
 * @author Wade
 * This is a A handler that handles exceptions that occur in the entire API.
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid or @Validated binding exception handler
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("handleMethodArgumentNotValidException", e);

        return new ResponseEntity<>(ErrorResponse.of(INVALID_INPUT_VALUE, e.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handling handler for unsupported HTTP method calls
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info("handleHttpRequestMethodNotSupportedException", e);

        return new ResponseEntity<>(ErrorResponse.of(METHOD_NOT_ALLOWED), HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Exception handling handler for Authentication exception
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.info("handleAccessDeniedException", e);

        return new ResponseEntity<>(ErrorResponse.of(HANDLE_ACCESS_DENIED), HttpStatus.UNAUTHORIZED);
    }

    /**
     * MethodArgumentTypeMismatchException handler
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info("handleMethodArgumentTypeMismatchException", e);

        return new ResponseEntity<>(ErrorResponse.of(HANDLE_METHOD_ARGUMENT_TYPE_MISMATCH), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handlers that handle the exception that occurs during business logic processing
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.info("handleBusinessException", e);

        return new ResponseEntity<>(ErrorResponse.of(e.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * server error handler
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException", e);

        return new ResponseEntity<>(ErrorResponse.of(INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
