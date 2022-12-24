package io.github.depromeet.knockknockbackend.domain.option.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryOptionsResponse {

    private final boolean newOption;

    private final boolean reactionOption;

    private final boolean nightOption;

}
