package road_to_employment.interview_helper.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import road_to_employment.interview_helper.auth.dto.TokenDto;
import road_to_employment.interview_helper.auth.service.TokenService;
import road_to_employment.interview_helper.global.security.CustomUserDetails;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("OAuth2 Login 성공!");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        TokenDto tokenDto = tokenService.createToken(userDetails);

        writeTokenResponse(response, tokenDto);
    }

    private void writeTokenResponse(HttpServletResponse response, TokenDto tokenDto)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String json = objectMapper.writeValueAsString(tokenDto);
        response.getWriter().write(json);
    }
}
