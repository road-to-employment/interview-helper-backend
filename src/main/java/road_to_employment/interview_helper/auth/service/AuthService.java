package road_to_employment.interview_helper.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import road_to_employment.interview_helper.auth.dto.TokenDto;
import road_to_employment.interview_helper.auth.security.JwtTokenProvider;
import road_to_employment.interview_helper.auth.service.TokenService;
import road_to_employment.interview_helper.global.error.exception.InvalidTokenException;
import road_to_employment.interview_helper.user.service.UserService;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserService userService;
    private final TokenService tokenService;

    public void logout(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        redisTemplate.delete(email);
        log.info("User logged out successfully: {}", email);
    }

    public TokenDto refresh(String refreshToken) {
        return tokenService.refreshToken(refreshToken);
    }
}
