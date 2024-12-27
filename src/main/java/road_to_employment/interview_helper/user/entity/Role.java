package road_to_employment.interview_helper.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자"),
    BLACKLIST("ROLE_BLACKLIST", "블랙리스트");

    private final String key;
    private final String title;
}
