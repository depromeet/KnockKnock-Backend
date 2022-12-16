package io.github.depromeet.knockknockbackend.domain.user.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoVO {

    private final Long id;
    private final String nickname;
    private final String profilePath;
    private final String email;

}
