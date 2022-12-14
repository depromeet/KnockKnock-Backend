package io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.asset.domain.ProfileImage;
import io.github.depromeet.knockknockbackend.domain.asset.domain.Thumbnail;
import lombok.Getter;

@Getter
public class ProfileImageDto {

    private Long id;
    private String url;

    public ProfileImageDto(ProfileImage profileImage) {
        this.id = profileImage.getId();
        this.url = profileImage.getProfileImageUrl();
    }
}
