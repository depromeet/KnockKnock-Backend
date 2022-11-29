package io.github.depromeet.knockknockbackend.global.utils.user;


import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.global.exception.UserNotFoundException;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserUtilsImpl implements UserUtils {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Override
    public List<User> findByIdIn(List<Long> ids) {
        return userRepository.findByIdIn(ids);
    }

}
