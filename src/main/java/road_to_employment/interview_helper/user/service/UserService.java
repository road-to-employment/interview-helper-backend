package road_to_employment.interview_helper.user.service;

import road_to_employment.interview_helper.user.entity.User;

public interface UserService {
    User findByEmail(String email);
    User findByProviderId(String providerId);
    User create(String name, String email, String picture, String provider, String providerId, String registerId);
}
