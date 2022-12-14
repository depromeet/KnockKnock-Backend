package io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ProfileImagesResponse {
    List<ProfileImageDto> profiles;
}
