package io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    @Size(max = 10)
    @NotEmpty
    private String nickname;

    @NotEmpty private String profilePath;
}
