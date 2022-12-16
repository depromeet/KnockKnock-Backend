package io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.asset.domain.Thumbnail;
import lombok.Getter;

@Getter
public class ThumbnailImageDto {


    private Long id;
    private String url;

    public ThumbnailImageDto(Thumbnail thumbnail) {
        this.id = thumbnail.getId();
        this.url = thumbnail.getImageUrl();
    }
}
