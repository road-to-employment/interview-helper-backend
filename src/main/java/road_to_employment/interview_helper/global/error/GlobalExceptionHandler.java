package road_to_employment.interview_helper.global.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import road_to_employment.interview_helper.global.error.exception.InvalidTokenException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException e) {
        log.error("Invalid token error: ", e);
        ErrorResponse response = new ErrorResponse("AUTH_ERROR", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleOAuth2AuthenticationException(OAuth2AuthenticationException e) {
        log.error("OAuth2 authentication error: ", e);
        ErrorResponse response = new ErrorResponse("AUTH_ERROR", "OAuth2 authentication failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @Data
    @AllArgsConstructor
    static class ErrorResponse {
        private String code;
        private String message;
    }

}
