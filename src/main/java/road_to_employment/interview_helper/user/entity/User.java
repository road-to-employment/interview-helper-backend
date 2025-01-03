package road_to_employment.interview_helper.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String picture;
    private String registerId;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 로그인 시, google이 들어감
    private String provider;

    // Google 로그인 한 유저의 고유 ID가 들어감
    private String providerId;
}
