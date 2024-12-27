package road_to_employment.interview_helper.oauth.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import road_to_employment.interview_helper.global.security.CustomUserDetails;
import road_to_employment.interview_helper.oauth.entity.OAuthAttributes;
import road_to_employment.interview_helper.user.entity.Role;
import road_to_employment.interview_helper.user.entity.User;
import road_to_employment.interview_helper.user.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본 OAuth2UserService 객체 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        // OAuth2UserService를 사용하여 OAuth2User 정보를 가져옴
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행시 키가 되는 필드값 프라이머리키와 같은 값(구글만 지원)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService를 사용하여 가져온 OAuth2User 정보로 oAuth2Attribute 객체를 만든다.
        OAuthAttributes oAuth2Attribute = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // oAuth2Attribute의 속성값들을 Map으로 반환받음
        Map<String, Object> userAttribute = oAuth2Attribute.convertToMap();

        String email = (String) userAttribute.get("email");
        Optional<User> findUser = userService.findByEmail(email);

        if (findUser.isEmpty()) {
            // 유저가 존재하지 않을 경우, userAttribute의 exist 값을 false로 넣어줌
            userAttribute.put("exist", false);
            Role defaultRole = Role.USER;
            userAttribute.put("role", defaultRole.getKey());
            // 유저의 권한(유저가 존재하지 않으므로 기본권한인 ROLE_USER를 넣어줌)
            // 회원속성, 속성이름을 사용해 DefaultOAuth2User 객체를 생성해 반환함
            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority(defaultRole.getKey())),
                    userAttribute, "email");
        }

        // 유저가 존재할 경우, userAttribute의 exist 값을 true로 넣어줌
        userAttribute.put("exist", true);
        Role userRole = findUser.get().getRole();
        userAttribute.put("role", userRole.getKey());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(userRole.getKey())),
                userAttribute, "email");
        // 로그인 한 유저 정보
//        User user = saveOrUpdate(attributes);
//
//        return new CustomUserDetails(user, attributes.getAttributes());
    }

    // User 저장하고 이미 있는 데이터면 Update
//    private User saveOrUpdate(OAuthAttributes attributes) {
//        User user = userRepository.findByEmail(attributes.getEmail())
//                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
//                .orElse(attributes.toEntity());
//        return userRepository.save(user);
//    }
}
