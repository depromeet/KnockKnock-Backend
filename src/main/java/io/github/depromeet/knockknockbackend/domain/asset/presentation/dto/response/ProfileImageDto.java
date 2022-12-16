package io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.asset.domain.ProfileImage;
import io.github.depromeet.knockknockbackend.domain.asset.domain.Thumbnail;
import lombok.Getter;

@Getter
public class ProfileImageDto {

    private Long id;
    private String url;
    private String title;
    public ProfileImageDto(ProfileImage profileImage) {
        this.id = profileImage.getId();
        this.url = profileImage.getImageUrl();
        this.title = profileImage.getTitle();
    }
}
