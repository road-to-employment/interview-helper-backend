package road_to_employment.interview_helper.oauth.service;

import road_to_employment.interview_helper.user.entity.User;

public interface RedisService {
    String setUserTokenToRedis(User user);
    String getValueByKey(String key);
    void deleteKey(String key);
}
