package io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response;


import io.github.depromeet.knockknockbackend.domain.group.domain.BackgroundImage;
import lombok.Getter;

@Getter
public class BackgroundImageDto {

    private Long backgroundId;
    private String url;

    public BackgroundImageDto(BackgroundImage backgroundImage) {
        this.backgroundId = backgroundImage.getId();
        this.url = backgroundImage.getBackgroundImageUrl();
    }
}
