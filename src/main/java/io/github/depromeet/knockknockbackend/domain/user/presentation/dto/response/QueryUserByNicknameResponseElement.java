package io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryUserByNicknameResponseElement {

    private final Long id;
    private final String nickname;
    private final String profilePath;

}
