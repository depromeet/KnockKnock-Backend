package io.github.depromeet.knockknockbackend.domain.reaction.presentation;

import io.github.depromeet.knockknockbackend.domain.reaction.presentation.dto.request.RegisterReactionRequest;
import io.github.depromeet.knockknockbackend.domain.reaction.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@Tag(name = "리액션 관련 컨트롤러", description = "")
@RequiredArgsConstructor
@RequestMapping("/reactions")
@RestController
public class ReactionController {

    private final ReactionService reactionService;

    @Operation(summary = "알림에 리액션 등록/수정")
    @PostMapping
    public ResponseEntity<Void> registerReaction(@RequestBody RegisterReactionRequest request) {
        reactionService.registerReaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
