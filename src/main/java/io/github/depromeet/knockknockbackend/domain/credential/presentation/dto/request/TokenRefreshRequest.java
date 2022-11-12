package io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class TokenRefreshRequest {
//    @NotEmpty()
    private String refreshToken;

}
