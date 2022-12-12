package io.github.depromeet.knockknockbackend.domain.recommendmessage.presentation.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryRecommendMessageResponse {

    private final List<QueryRecommendMessageResponseElement> recommendMessage;

}
