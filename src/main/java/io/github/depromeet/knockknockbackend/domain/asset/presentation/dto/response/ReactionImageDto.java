package io.github.depromeet.knockknockbackend.domain.asset.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.asset.domain.ProfileImage;
import io.github.depromeet.knockknockbackend.domain.asset.domain.Reaction;
import lombok.Getter;

@Getter
public class ReactionImageDto {

    private Long id;
    private String url;

    private String title;

    public ReactionImageDto(Reaction reaction) {
        this.id = reaction.getId();
        this.url = reaction.getImageUrl();
        this.title = reaction.getTitle();
    }
}
