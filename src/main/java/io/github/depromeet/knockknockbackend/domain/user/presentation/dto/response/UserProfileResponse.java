package io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfileResponse {
    private final Long id;
    private final String nickname;
    private final String profilePath;

    public UserProfileResponse(UserInfoVO userInfo) {
        this.id = userInfo.getId();
        this.nickname = userInfo.getNickname();
        this.profilePath = userInfo.getProfilePath();
    }
}
