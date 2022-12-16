package io.github.depromeet.knockknockbackend.domain.group.presentation;

import io.github.depromeet.knockknockbackend.domain.group.domain.usecase.AdmissionUsecase;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.AddFriendToGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateCategoryRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateFriendGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateOpenGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.GroupInTypeRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.UpdateGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoListResponse;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.CategoryDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.CategoryListResponse;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupInviteLinkResponse;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupBriefInfoDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupResponse;
import io.github.depromeet.knockknockbackend.domain.group.service.CategoryService;
import io.github.depromeet.knockknockbackend.domain.group.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@Tag(name = "그룹 관련 컨트롤러", description = "")
@SecurityRequirement(name = "access-token")
public class GroupController {


    private final GroupService groupService;

    private final CategoryService categoryService;

    private final AdmissionUsecase admissionUsecase;

    @Operation(summary = "공개 그룹을 만듭니다")
    @PostMapping("/open")
    public GroupResponse createOpenGroup(@Valid @RequestBody CreateOpenGroupRequest createOpenGroupRequest){
        return groupService.createOpenGroup(createOpenGroupRequest);
    }

    @Operation(summary = "친구 그룹을 만듭니다")
    @PostMapping("/friend")
    public GroupResponse createFriendGroup(@Valid @RequestBody CreateFriendGroupRequest createFriendGroupRequest){
        return groupService.createFriendGroup(createFriendGroupRequest);
    }

    @Operation(summary = "방장 권한 그룹 설정")
    @PutMapping("/{id}")
    public GroupResponse putGroup(@PathVariable("id") Long groupId ,@Valid @RequestBody UpdateGroupRequest updateGroupRequest){

        return groupService.updateGroup(groupId,updateGroupRequest);
    }

    @Operation(summary = "방장 권한 그룹 제거")
    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable("id") Long groupId){
        groupService.deleteGroup(groupId);
    }


    @GetMapping("/{id}")
    public GroupResponse getGroupDetail(@PathVariable("id") Long groupId){
        return groupService.getGroupDetailById(groupId);
    }


    @Parameter(name = "type", description = "type", schema = @Schema(implementation = GroupInTypeRequest.class)
        , in = ParameterIn.QUERY)
    @Operation(summary = "참여중인 그룹 목록 전체 홀로외침 친구들 방 필터링")
    @GetMapping("/joined")
    public Slice<GroupBriefInfoDto> getParticipatingGroups(
        @RequestParam("type") GroupInTypeRequest groupInTypeRequest,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size" ,defaultValue = "10") Integer size){
        PageRequest pageRequest = PageRequest.of(page, size);
        if(groupInTypeRequest == GroupInTypeRequest.ALL){
            return groupService.findAllJoinedGroups(pageRequest);
        }
        return groupService.findJoinedGroupByType(groupInTypeRequest,pageRequest);
    }


    @Parameter(name = "category", description = "category", schema = @Schema(implementation = Long.class)
        , in = ParameterIn.QUERY)
    @Operation(summary = "방 찾기")
    @GetMapping("/open")
    public Slice<GroupBriefInfoDto> getAllOpenGroups(
        @RequestParam(value = "category" ) Long categoryId,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size" ,defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return groupService.findOpenGroupByCategory(categoryId ,pageRequest);
    }

    @GetMapping("/categories")
    public CategoryListResponse getCategory(){
        return categoryService.findAllCategory();
    }

    @GetMapping("/categories/famous")
    public CategoryListResponse getFamousCategory(){
        return categoryService.findFamousCategory();
    }

    //TODO : 관리자권한 필요
    @PostMapping("/categories")
    public CategoryDto createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest){
        return categoryService.saveCategory(createCategoryRequest);
    }



    @Operation(summary = "그룹에 가입요청을 합니다.")
    @PostMapping("/{id}/admissions")
    public AdmissionInfoDto admissionToGroup(@PathVariable(value = "id") Long groupId){
        return admissionUsecase.requestAdmission(groupId);
    }

    @Operation(summary = "그룹 가입 요청을 살펴봅니다.")
    @GetMapping("/{id}/admissions")
    public AdmissionInfoListResponse getAdmissionRequest(@PathVariable(value = "id") Long groupId){
        return admissionUsecase.getAdmissions(groupId);
    }

    @Operation(summary = "그룹 가입요청을 허락합니다.")
    @PostMapping("/{id}/admissions/{admission_id}/allow")
    public AdmissionInfoDto acceptAdmissionRequest(
        @PathVariable(value = "id") Long groupId,
        @PathVariable(value = "admission_id") Long admissionId){
        return admissionUsecase.acceptAdmission(groupId,admissionId);
    }

    @Operation(summary = "그룹 가입요청을 거절합니다.")
    @PostMapping("/{id}/admissions/{admission_id}/refuse")
    public AdmissionInfoDto refuseAdmissionRequest(
        @PathVariable(value = "id") Long groupId,
        @PathVariable(value = "admission_id") Long admissionId
    ) {
        return admissionUsecase.refuseAdmission(groupId, admissionId);
    }
    @Operation(summary = "방 검색하기")
    @GetMapping("/search/{searchString}")
    public Slice<GroupBriefInfoDto> searchOpenGroups(
        @PathVariable(value = "searchString") String searchString,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size" ,defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return groupService.searchOpenGroups(searchString,pageRequest);
    }

    @Operation(summary = "방장 권한 멤버 추가")
    @PostMapping("/{id}/members")
    public GroupResponse addMembers(
        @PathVariable(value = "id") Long groupId,
        @Valid @RequestBody AddFriendToGroupRequest addFriendToGroupRequest){
        return groupService.addMembersToGroup(groupId , addFriendToGroupRequest.getMemberIds());
    }

    @Operation(summary = "그룹에서 나가기 ( 방장은 못나갑니다 )")
    @DeleteMapping("/{id}/members/leave")
    public GroupResponse leaveFromGroup(@PathVariable(value = "id") Long groupId){
        return groupService.leaveFromGroup(groupId);
    }

    @Operation(summary = "멤버 내쫓기 (방장권한)")
    @DeleteMapping("/{id}/members/{user_id}")
    public GroupResponse deleteMemberFromGroup(@PathVariable(value = "id") Long groupId, @PathVariable(value = "user_id") Long userId){
        return groupService.deleteMemberFromGroup(groupId , userId);
    }

    @Operation(summary = "그룹 초대 토큰 발급")
    @GetMapping("/{id}/members/invite")
    public GroupInviteLinkResponse createGroupInviteLink(
        @PathVariable(value = "id") Long groupId
    ){
        return groupService.createGroupInviteLink(groupId);
    }

    @Operation(summary = "그룹 초대 토큰 검증 & 그룹 가입")
    @PostMapping("/{id}/members/invite/{code}")
    public GroupResponse checkGroupInviteLink(
        @PathVariable(value = "id") Long groupId,
        @PathVariable(value = "code") String code
    ){
        return groupService.checkGroupInviteLink(groupId,code);
    }

}
