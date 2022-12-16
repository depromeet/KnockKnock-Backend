package io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response;


import io.github.depromeet.knockknockbackend.domain.asset.domain.BackgroundImage;
import lombok.Getter;

@Getter
public class BackgroundImageDto {

    private Long id;
    private String url;

    public BackgroundImageDto(BackgroundImage backgroundImage) {
        this.id = backgroundImage.getId();
        this.url = backgroundImage.getImageUrl();
    }
}
