package road_to_employment.interview_helper.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import road_to_employment.interview_helper.auth.dto.TokenDto;
import road_to_employment.interview_helper.auth.security.JwtTokenProvider;
import road_to_employment.interview_helper.global.error.exception.InvalidTokenException;
import road_to_employment.interview_helper.global.security.CustomUserDetails;
import road_to_employment.interview_helper.user.entity.User;
import road_to_employment.interview_helper.user.repository.UserRepository;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    public TokenDto createToken(CustomUserDetails userDetails) {
        String accessToken = jwtTokenProvider.createAccessToken(userDetails);
        String refreshToken = jwtTokenProvider.createRefreshToken(userDetails);

        //TTL 60초로 설정
        long refreshTokenValiditySeconds = jwtTokenProvider.getRefreshTokenValidityInMilliseconds() / 1000;

        redisTemplate.opsForValue().set(
                userDetails.getEmail(),
                refreshToken,
                refreshTokenValiditySeconds,
                TimeUnit.MICROSECONDS);

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDto refreshToken(String refreshToken) {
        // refresh token 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new InvalidTokenException("Refresh token is not valid");
        }

        // refresh token에서 이메일 추출
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);

        // Redis에서 저장된 refresh token 가져오기
        String savedRefreshToken = redisTemplate.opsForValue().get(email);
        if (!refreshToken.equals(savedRefreshToken)) {
            throw new InvalidTokenException("Refresh token is not matched");
        }

        return createNewTokens(email);
    }

    private TokenDto createNewTokens(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        CustomUserDetails userDetails = new CustomUserDetails(user, new HashMap<>());
        return createToken(userDetails);
    }
}
