package io.github.depromeet.knockknockbackend.domain.relation.presentation;

import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.request.SendFriendRequest;
import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.response.QueryFriendListResponse;
import io.github.depromeet.knockknockbackend.domain.relation.service.RelationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "유저 관련 컨트롤러", description = "")
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@RequestMapping("/relations")
@RestController
public class RelationController {

    private final RelationService relationService;

    @Operation(summary = "내 친구 리스트를 가져오는 Api입니다. - 친구목록&그룹")
    @GetMapping
    public QueryFriendListResponse queryFriendList() {
        return relationService.queryFriendList();
    }

    @Operation(summary = "친구 요청을 보내는 Api입니다. - 친구목록")
    @PostMapping
    public ResponseEntity<Void> sendUserRequest(@RequestBody @Valid SendFriendRequest request) {
        return new ResponseEntity<>(relationService.sendFriendRequest(request));
    }

}
