package road_to_employment.interview_helper.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import road_to_employment.interview_helper.auth.dto.LogoutRequest;
import road_to_employment.interview_helper.auth.dto.TokenDto;
import road_to_employment.interview_helper.auth.dto.TokenRefreshRequest;
import road_to_employment.interview_helper.auth.service.AuthService;
import road_to_employment.interview_helper.auth.service.TokenService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestBody TokenRefreshRequest request) {
        log.info("auth controller -> refresh() called!");
        TokenDto tokenDto = tokenService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok().build();
    }
}
