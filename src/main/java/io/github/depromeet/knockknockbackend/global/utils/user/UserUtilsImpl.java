package io.github.depromeet.knockknockbackend.global.utils.user;


import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.global.exception.UserNotFoundException;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserUtilsImpl implements UserUtils {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Override
    public List<User> findByIdIn(List<Long> ids) {
        return userRepository.findByIdIn(ids);
    }

    public User getUserFromSecurityContext() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = getUserById(currentUserId);
        return user;
    }
}
