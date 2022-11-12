package io.github.depromeet.knockknockbackend.global.utils.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleInformationResponse {

    private String email;

    private String id;

}
