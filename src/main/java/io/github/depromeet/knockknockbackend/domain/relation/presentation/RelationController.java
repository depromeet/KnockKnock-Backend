package io.github.depromeet.knockknockbackend.domain.relation.presentation;


import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.request.FriendRequest;
import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.response.QueryFriendListResponse;
import io.github.depromeet.knockknockbackend.domain.relation.service.RelationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 관련 컨트롤러", description = "")
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@RequestMapping("/api/v1/relations")
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
    public ResponseEntity<Void> sendUserRequest(@RequestBody @Valid FriendRequest request) {
        return new ResponseEntity<>(relationService.sendFriendRequest(request));
    }

    @Operation(summary = "친구를 삭제하는 Api입니다. - 친구목록")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteRelation(@RequestBody @Valid FriendRequest request) {
        relationService.deleteRelation(request);
    }

    @Operation(summary = "친구 요청을 수락하는 Api입니다. - 메인 알림")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/requests")
    public void acceptRequest(@RequestBody @Valid FriendRequest request) {
        relationService.acceptRequest(request);
    }

    @Operation(summary = "친구 요청을 거절하는 Api입니다. - 메인 알림")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/requests")
    public void refuseRequest(@RequestBody @Valid FriendRequest request) {
        relationService.refuseRequest(request);
    }
}
