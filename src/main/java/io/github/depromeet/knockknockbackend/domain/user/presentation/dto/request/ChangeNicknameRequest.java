package io.github.depromeet.knockknockbackend.domain.user.presentation.dto.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeNicknameRequest {

    @Size(max = 10)
    @NotEmpty
    private String nickname;
}
