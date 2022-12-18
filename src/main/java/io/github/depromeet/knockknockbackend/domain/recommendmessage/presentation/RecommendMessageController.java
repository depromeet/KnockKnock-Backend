package io.github.depromeet.knockknockbackend.domain.recommendmessage.presentation;


import io.github.depromeet.knockknockbackend.domain.recommendmessage.presentation.dto.response.QueryRecommendMessageResponse;
import io.github.depromeet.knockknockbackend.domain.recommendmessage.service.RecommendMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@Tag(name = "추천메세지 관련 컨트롤러", description = "")
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendmessage")
@RestController
public class RecommendMessageController {

    private final RecommendMessageService recommendMessageService;

    @Operation(summary = "추천메세지 조회")
    @GetMapping
    public QueryRecommendMessageResponse queryRecommendMessageContentByUseY() {
        return recommendMessageService.queryRecommendMessageContentByUseY();
    }
}
