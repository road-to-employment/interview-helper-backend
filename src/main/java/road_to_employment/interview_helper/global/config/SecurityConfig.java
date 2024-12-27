package road_to_employment.interview_helper.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import road_to_employment.interview_helper.auth.security.JwtTokenProvider;
import road_to_employment.interview_helper.oauth.handler.OAuth2SuccessHandler;
import road_to_employment.interview_helper.oauth.service.CustomOAuth2UserService;
import road_to_employment.interview_helper.user.repository.UserRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oauth2SuccessHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth")
                        .permitAll()
                        .requestMatchers("/board/**").hasAnyRole("NORMAL", "ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(
                        oauth2 -> oauth2.userInfoEndpoint(
                                        userInfo -> userInfo
                                                .userService(customOAuth2UserService))
                                .successHandler(oauth2SuccessHandler));
        return http.build();
    }
}