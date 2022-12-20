package io.github.depromeet.knockknockbackend.global.utils.api.dto.response;


import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OIDCPublicKeysResponse {
    List<OIDCPublicKeyDto> keys;
}
