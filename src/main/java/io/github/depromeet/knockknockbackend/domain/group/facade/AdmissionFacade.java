package io.github.depromeet.knockknockbackend.domain.group.facade;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUsers;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.AddFriendToGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoListResponse;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupResponse;
import io.github.depromeet.knockknockbackend.domain.group.service.AdmissionService;
import io.github.depromeet.knockknockbackend.domain.group.service.GroupService;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.relation.RelationUtils;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class AdmissionFacade {

    private final AdmissionService admissionService;
    private final GroupService groupService;

    private final RelationUtils relationUtils;

    public AdmissionInfoDto requestAdmission(Long groupId) {
        Group group = groupService.queryGroup(groupId);
        return admissionService.requestAdmission(group);
    }

    public AdmissionInfoListResponse getAdmissions(Long groupId) {
        Group group = groupService.queryGroup(groupId);
        return admissionService.getAdmissions(group);
    }

    public AdmissionInfoDto acceptAdmission(Long groupId, Long admissionId) {
        Group group = groupService.queryGroup(groupId);

        //TODO: 멤버 추가 해줘야함 58번 이슈 머지 되고나서 가능

        return admissionService.acceptAdmission(group,admissionId);
    }

    public AdmissionInfoDto refuseAdmission(Long groupId, Long admissionId) {
        Group group = groupService.queryGroup(groupId);
        return admissionService.refuseAdmission(group,admissionId);
    }

    public GroupResponse addMembersToGroup(Long groupId, List<Long> requestMemberIds) {
        // 이미 방안에 들어갔는지 검증
        Group group = groupService.queryGroup(groupId);
        GroupUsers groupUsers = group.getGroupUsers();
        groupUsers.validInviteUsersAlreadyEnterGroup(requestMemberIds);
        // 내 친구 목록 불러오기
        Long userId = SecurityUtils.getCurrentUserId();
        List<Long> myFriendList = relationUtils.findMyFriendUserIdList(userId);

        //요청한 목록 중에서 내 친구 인 사람
        List<Long> addMemberList = requestMemberIds.stream().filter(id ->
            myFriendList.contains(id)
        ).collect(Collectors.toList());
        //요청한 목록 중에서 내 친구 아닌 사람
        List<Long> requestAdmissionIds = requestMemberIds.stream().filter(id ->
            !myFriendList.contains(id)
        ).collect(Collectors.toList());

        // 초대 요청 보내기
        admissionService.requestAdmissions(group ,requestAdmissionIds);
        // 내 친구 목록에 있는 사람들은 그냥 방안에 넣기
        return groupService.addMembersToGroup(groupId ,addMemberList);
    }
}
