package io.github.depromeet.knockknockbackend.domain.user.service;


import io.github.depromeet.knockknockbackend.domain.user.UserRelationService;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.request.ChangeProfileRequest;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.QueryUserByNicknameResponse;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.QueryUserByNicknameResponseElement;
import io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response.UserProfileResponse;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserUtils userUtils;
    private final UserRelationService userRelationService;

    public QueryUserByNicknameResponse queryUserByNicknameResponse(String nickname) {
        List<QueryUserByNicknameResponseElement> result =
                userRepository.findByNicknameContaining(nickname).stream()
                        .map(
                                user ->
                                        new QueryUserByNicknameResponseElement(
                                                user.getId(),
                                                user.getNickname(),
                                                user.getProfilePath(),
                                                userRelationService.getIsFriend(user.getId())))
                        .collect(Collectors.toList());

        return new QueryUserByNicknameResponse(result);
    }

    public void changeNickname(String nickname) {
        User user = userUtils.getUserById(SecurityUtils.getCurrentUserId());

        user.changeNickname(nickname);

        userRepository.save(user);
    }

    public UserProfileResponse changeProfile(ChangeProfileRequest changeProfileRequest) {
        User user = userUtils.getUserFromSecurityContext();
        user.changeProfile(
                changeProfileRequest.getNickname(), changeProfileRequest.getProfilePath());

        return new UserProfileResponse(user.getUserInfo());
    }

    public UserProfileResponse getProfile() {
        User user = userUtils.getUserFromSecurityContext();

        return new UserProfileResponse(user.getUserInfo());
    }
}
