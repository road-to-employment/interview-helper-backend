package road_to_employment.interview_helper.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import road_to_employment.interview_helper.user.entity.Role;
import road_to_employment.interview_helper.user.entity.User;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
public class RegisterDto {

    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String provider;

    private String picture;
    private Role role;

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .provider(provider)
                .role(Objects.requireNonNullElse(role, Role.USER))
                .build();
    }
}
