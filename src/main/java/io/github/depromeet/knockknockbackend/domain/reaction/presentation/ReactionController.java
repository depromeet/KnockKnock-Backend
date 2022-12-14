package io.github.depromeet.knockknockbackend.domain.reaction.presentation;


import io.github.depromeet.knockknockbackend.domain.reaction.presentation.dto.request.RegisterReactionRequest;
import io.github.depromeet.knockknockbackend.domain.reaction.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@Tag(name = "리액션 관련 컨트롤러", description = "")
@RequiredArgsConstructor
@RequestMapping("/api/v1/reactions")
@RestController
public class ReactionController {

    private final ReactionService reactionService;

    @Operation(summary = "알림에 리액션 등록")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void registerReaction(@Valid @RequestBody RegisterReactionRequest request) {
        reactionService.registerReaction(request);
    }

    @Operation(summary = "알림 리액션 수정")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("{notification_reaction_id}")
    public void changeReaction(
            @PathVariable("notification_reaction_id") Long notificationReactionId,
            @Valid @RequestBody RegisterReactionRequest request) {
        reactionService.changeReaction(notificationReactionId, request);
    }

    @Operation(summary = "알림 리액션 삭제")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{notification_reaction_id}")
    public void deleteReaction(
            @PathVariable("notification_reaction_id") Long notificationReactionId) {
        reactionService.deleteReaction(notificationReactionId);
    }
}
