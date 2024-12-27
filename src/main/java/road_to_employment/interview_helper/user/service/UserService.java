package road_to_employment.interview_helper.user.service;

import road_to_employment.interview_helper.user.dto.request.RegisterDto;
import road_to_employment.interview_helper.user.entity.User;

import java.util.Optional;

public interface UserService {
    User saveUser(RegisterDto registerDto);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User updateUser(String email, String name, String picture);
}
