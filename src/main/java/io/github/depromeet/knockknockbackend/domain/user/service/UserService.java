package io.github.depromeet.knockknockbackend.domain.user.service;

import io.github.depromeet.knockknockbackend.domain.user.UserUtils;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.QueryUserByNicknameResponse;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.QueryUserByNicknameResponseElement;
import io.github.depromeet.knockknockbackend.global.exception.UserNotFoundException;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserUtils {

    private final UserRepository userRepository;

    public QueryUserByNicknameResponse queryUserByNicknameResponse(String nickname) {
        List<QueryUserByNicknameResponseElement> result = userRepository.findByNicknameLike(nickname)
                .stream().map(
                        user -> new QueryUserByNicknameResponseElement(
                                user.getId(),
                                user.getNickname(),
                                user.getProfilePath()
                        ))
                .collect(Collectors.toList());

        return new QueryUserByNicknameResponse(result);
    }

    public void changeNickname(String nickname) {
        User user = getUserById(SecurityUtils.getCurrentUserId());

        user.changeNickname(nickname);

        userRepository.save(user);
    }

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
