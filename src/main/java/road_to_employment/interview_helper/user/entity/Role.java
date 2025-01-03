package road_to_employment.interview_helper.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Role {
    USER("USER", "일반 사용자"),
    ADMIN("ADMIN", "관리자");

    private final String name;
    private final String title;

    private Role(String name, String title) {
        this.name = name;
        this.title = title;
    }
}
