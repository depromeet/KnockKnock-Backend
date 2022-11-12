package io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class UserProfileDto {

    private Long userId;
    private String nickName;

    public UserProfileDto(User user){
        userId = user.getId();
        nickName = user.getNickname();

    }


}
