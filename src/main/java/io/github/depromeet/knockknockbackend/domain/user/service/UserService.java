package io.github.depromeet.knockknockbackend.domain.user.service;

import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.QueryUserByNicknameResponse;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.QueryUserByNicknameResponseElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

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

}
