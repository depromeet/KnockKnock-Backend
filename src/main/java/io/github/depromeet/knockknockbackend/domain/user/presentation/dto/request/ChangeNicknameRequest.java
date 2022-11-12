package io.github.depromeet.knockknockbackend.domain.user.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class ChangeNicknameRequest {

    @Size(max = 10)
    @NotEmpty
    private String nickname;

}
