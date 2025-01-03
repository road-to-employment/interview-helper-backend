package road_to_employment.interview_helper.oauth.service;

import org.springframework.http.ResponseEntity;

public interface OAuthService {
    String googleLoginAddress();
    String requestAccessToken(String code);
    ResponseEntity<String> requestUserInfo(String accessToken);
}
