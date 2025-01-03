package road_to_employment.interview_helper.oauth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import road_to_employment.interview_helper.oauth.service.OAuthService;
import road_to_employment.interview_helper.oauth.service.RedisService;
import road_to_employment.interview_helper.user.entity.User;
import road_to_employment.interview_helper.user.service.UserService;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService oAuthService;
    private final UserService userService;
    private final RedisService redisService;

    @GetMapping("/google")
    public ResponseEntity<String> googleOauthUri() {
        log.info("controller -> googleOauthUri() called!");
        String redirectUri = oAuthService.googleLoginAddress();

        return ResponseEntity.ok(redirectUri);
    }

    @PostMapping("/google/access-token")
    public String googleAccessTokenUri(@RequestBody Map<String, String> body) {
        log.info("controller -> googleAccessTokenUri() called!");
        String code = body.get("code");
        String accessToken = oAuthService.requestAccessToken(code);
        log.info("controller -> googleAccessTokenUri() accessToken: {}", accessToken);

        return accessToken;
    }

    @PostMapping("/google/userinfo")
    public ResponseEntity<String> googleUserInfoUri(@RequestBody Map<String, String> body) {
        log.info("controller -> googleUserInfoUri() called!");
        String accessToken = body.get("accessToken");
        ResponseEntity<String> response = oAuthService.requestUserInfo(accessToken);

        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/google/redis-user-token")
    public ResponseEntity<String> saveUserTokenToRedis(@RequestBody Map<String, Map<String, String>> userInfo) {
        log.info("controller -> saveUserTokenToRedis() called!");
        Map<String, String> body = userInfo.get("userinfo");
        String providerId = body.get("id");
        String name = body.get("name");
        String email = body.get("email");
        String picture = body.get("picture");
        String provider = "google";
        String registerId = provider + '_' + providerId;
        log.info("controller -> saveUserTokenToRedis() providerId: {}", providerId);
        User user = userService.findByProviderId(providerId);

        if (user == null) {
            user = userService.create(name, email, picture, provider, providerId, registerId);
        }

        String response = redisService.setUserTokenToRedis(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/google/get-redis-user-token")
    public String getUserTokenFromRedis(@RequestBody Map<String, String> body) {
        log.info("controller -> getUserTokenFromRedis() called!");
        String userToken = body.get("userToken");

        String response = redisService.getValueByKey(userToken);
        log.info("controller -> getUserTokenFromRedis() response: {}", response);
        return response;
    }

    @PostMapping("/google/delete-redis-user-token")
    public boolean deleteUserTokenFromRedis(@RequestBody Map<String, String> body) {
        log.info("controller -> deleteUserTokenFromRedis() called!");
        String userToken = body.get("userToken");

        String isUserToken = redisService.getValueByKey(userToken);

        if (Objects.equals(isUserToken, "해당 userToken과 일치하는 사용자가 없습니다!")) {
            return false;
        }

        redisService.deleteKey(userToken);

        return true;
    }
}
