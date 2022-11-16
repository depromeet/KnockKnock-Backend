package io.github.depromeet.knockknockbackend.domain.group.presentation;

import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AfterOauthResponse;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateFriendGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateOpenGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.CreateFriendGroupResponse;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.CreateOpenGroupResponse;
import io.github.depromeet.knockknockbackend.domain.group.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@Tag(name = "그룹 관련 컨트롤러", description = "")
@SecurityRequirement(name = "access-token")
public class GroupController {


    private final GroupService groupService;



    @Operation(summary = "공개 그룹을 만듭니다")
    @PostMapping("/open")
    public CreateOpenGroupResponse createOpenGroup(@Valid @RequestBody CreateOpenGroupRequest createOpenGroupRequest){
        return this.groupService.createOpenGroup(createOpenGroupRequest);
    }

    @Operation(summary = "친구 그룹을 만듭니다")
    @PostMapping("/friend")
    public CreateFriendGroupResponse createFriendGroup(@Valid @RequestBody CreateFriendGroupRequest createFriendGroupRequest){
        return this.groupService.createFriendGroup(createFriendGroupRequest);
    }

}
