package io.github.depromeet.knockknockbackend.domain.recommendmessage.service;

import io.github.depromeet.knockknockbackend.domain.recommendmessage.domain.RecommendMessage;
import io.github.depromeet.knockknockbackend.domain.recommendmessage.domain.repository.RecommendMessageRepository;
import io.github.depromeet.knockknockbackend.domain.recommendmessage.presentation.dto.response.QueryRecommendMessageResponse;
import io.github.depromeet.knockknockbackend.domain.recommendmessage.presentation.dto.response.QueryRecommendMessageResponseElement;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RecommendMessageService {

    private final RecommendMessageRepository recommendMessageRepository;

    @Transactional
    public QueryRecommendMessageResponse queryRecommendMessageContentByUseY() {
        RecommendMessage recommendMessage = recommendMessageRepository.findAllByUseYn(true);

        return new QueryRecommendMessageResponse(
            recommendMessage.getRecommendMessageContents().stream()
                .map(recommendMessageContent ->
                    new QueryRecommendMessageResponseElement(recommendMessageContent.getContent())
                ).collect(Collectors.toList())
        );
    }

}
