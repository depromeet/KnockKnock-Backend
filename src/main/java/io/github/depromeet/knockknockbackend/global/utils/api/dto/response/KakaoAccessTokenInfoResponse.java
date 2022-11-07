package io.github.depromeet.knockknockbackend.global.utils.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoAccessTokenInfoResponse {
    @JsonProperty("app_id")
    private String appId;
}
