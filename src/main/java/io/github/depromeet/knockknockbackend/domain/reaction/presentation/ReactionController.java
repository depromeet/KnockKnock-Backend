package io.github.depromeet.knockknockbackend.domain.reaction.presentation;


import io.github.depromeet.knockknockbackend.domain.reaction.presentation.dto.request.RegisterReactionRequest;
import io.github.depromeet.knockknockbackend.domain.reaction.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "알림에 리액션 등록")
    @PostMapping
    public void registerReaction(@RequestBody RegisterReactionRequest request) {
        reactionService.registerReaction(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "알림에 리액션 수정")
    @PatchMapping("{notification_reaction_id}")
    public void changeReaction(
            @PathVariable("notification_reaction_id") Long notificationReactionId,
            @RequestBody RegisterReactionRequest request) {
        reactionService.changeReaction(notificationReactionId, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{notification_reaction_id}")
    public void deleteReaction(
            @PathVariable("notification_reaction_id") Long notificationReactionId) {
        reactionService.deleteReaction(notificationReactionId);
    }
}
