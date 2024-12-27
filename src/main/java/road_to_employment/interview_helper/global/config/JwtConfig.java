package road_to_employment.interview_helper.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import road_to_employment.interview_helper.auth.security.JwtTokenProvider;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(secretKey, 3600000, 604800000);
    }
}
