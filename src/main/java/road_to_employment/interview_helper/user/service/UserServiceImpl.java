package road_to_employment.interview_helper.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import road_to_employment.interview_helper.user.entity.User;
import road_to_employment.interview_helper.user.repository.UserRepository;
import road_to_employment.interview_helper.user.dto.request.RegisterDto;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findByUserNo(Long userNo) {
        return userRepository.findById(userNo)
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User saveUser(RegisterDto registerDto) {
        userRepository.findByEmail(registerDto.getEmail())
                .ifPresent(user -> {throw new IllegalArgumentException("이미 존재하는 회원입니다.");});

        User user = registerDto.toEntity();

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    @Override
    @Transactional
    public void removeUser(SecurityUserDto user) {
        User findUser = userRepository.findById(user.getUserNo())
                .orElseThrow(IllegalStateException::new);
        userRepository.delete(findUser);
    }

    @Override
    @Transactional
    public User updateUser(String email, String name, String picture) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return user.update(name, picture);
    }
}
