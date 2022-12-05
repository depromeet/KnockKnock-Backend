package io.github.depromeet.knockknockbackend.domain.group.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group.GroupBuilder;
import io.github.depromeet.knockknockbackend.domain.group.domain.Category;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUsers;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.CategoryRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupUserRepository;
import io.github.depromeet.knockknockbackend.domain.group.exception.CategoryNotFoundException;
import io.github.depromeet.knockknockbackend.domain.group.exception.GroupNotFoundException;
import io.github.depromeet.knockknockbackend.domain.group.exception.HostCanNotLeaveGroupException;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateFriendGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateOpenGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.GroupInTypeRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.UpdateGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.CreateGroupResponse;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupBriefInfoDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupBriefInfoListResponse;
import io.github.depromeet.knockknockbackend.global.utils.relation.RelationUtils;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupResponse;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.exception.UserNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;
    private final CategoryRepository categoryRepository;


    private final ThumbnailImageService thumbnailImageService;
    private final BackgroundImageService backgroundImageService;

    private final InviteService inviteService;
    private final UserUtils userUtils;
    private final RelationUtils relationUtils;


    /**
     * 카테고리 정보를 가져옵니다.
     * @param categoryId 카테고리 아이디
     * @throws CategoryNotFoundException
     */
    private Category queryGroupCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> CategoryNotFoundException.EXCEPTION);
    }

    /**
     * 그룹 정보를 가져옵니다.
     * @param groupId 그룹 아이디
     * @return
     */
    public Group queryGroup(Long groupId) {
        return groupRepository.findById(groupId)
            .orElseThrow(() -> GroupNotFoundException.EXCEPTION);
    }


    /**
     * 방에 집어넣을 유저가 존재하는 유저인지 판단합니다.
     * @param findUserList
     * @param requestUserIdList
     * @throws UserNotFoundException
     */
    private void validReqMemberNotExist(List<User> findUserList, List<Long> requestUserIdList
    ) {
        //요청한 유저중에 없는 유저가 있으면 안됩니다.
        if(requestUserIdList.size() != findUserList.size()){
            throw UserNotFoundException.EXCEPTION;
        }
    }


    /**
     * 오픈 그룹(홀로 외침방 )을 생성합니다.
     * @return CreateGroupResponse
     */
    public CreateGroupResponse createOpenGroup(CreateOpenGroupRequest createOpenGroupRequest) {
        // 요청자 정보 시큐리티에서 가져옴
        User reqUser = userUtils.getUserFromSecurityContext();
        // 혹시 본인 아이디 멤버로 넣었으면 지워버리기.
        createOpenGroupRequest.getMemberIds().removeIf(id -> reqUser.getId().equals(id));
        //요청받은 id 목록들로 디비에서 조회
        List<User> findUserList = userUtils.findByIdIn(createOpenGroupRequest.getMemberIds());

        // 요청받은 유저 아이디 목록이 디비에 존재하는 지 확인
        validReqMemberNotExist(findUserList, createOpenGroupRequest.getMemberIds());
        // 오픈 그룹 만들기
        Group group = makeOpenGroup(createOpenGroupRequest);
        groupRepository.save(group);
        // 그룹 유저 리스트 추가
        GroupUsers groupUsers = GroupUsers.createGroupUsers(reqUser, findUserList, group);
        groupUserRepository.saveAll(groupUsers.getGroupUserList());

        return new CreateGroupResponse(
            group.getGroupBaseInfoVo() ,
            groupUsers.getUserInfoVoList()
            ,true);
    }

    /**
     * 친구그룹을 만듭니다
     * @return CreateGroupResponse
     */
    public CreateGroupResponse createFriendGroup(CreateFriendGroupRequest createFriendGroupRequest) {
        User reqUser = userUtils.getUserFromSecurityContext();

        //TODO : 요청 받은 memeberId가 친구 목록에 속해있는지 검증.
        List<Long> memberIds = createFriendGroupRequest.getMemberIds();
        memberIds.removeIf(id -> reqUser.getId().equals(id));
        //요청받은 id 목록들로 디비에서 조회
        List<User> requestUserList = userUtils.findByIdIn(memberIds);
        // 그룹 만들기
        Group group = makeFriendGroup();
        groupRepository.save(group);
        // 그룹 유저 리스트만들기
        GroupUsers groupUsers = GroupUsers.createGroupUsers(reqUser, requestUserList, group);

        groupUserRepository.saveAll(groupUsers.getGroupUserList());


        return new CreateGroupResponse(
            group.getGroupBaseInfoVo() ,
            groupUsers.getUserInfoVoList()
            ,true);
    }

    /**
     * 오픈 그룹 생성 로직 뺀 함수입니다..
     * 썸네일, 백그라운드 서비스를 파사드로 빼고
     * 그룹 도메인으로 옮길 예정입니다.
     * @return Group
     */
    private Group makeOpenGroup(CreateOpenGroupRequest createOpenGroupRequest) {
        GroupBuilder groupBuilder = Group.builder()
            .publicAccess(createOpenGroupRequest.getPublicAccess())
            .thumbnailPath(
                createOpenGroupRequest.getThumbnailPath() == null ?
                    thumbnailImageService.getRandomThumbnailUrl() :
                    createOpenGroupRequest.getThumbnailPath()
            )
            .backgroundImagePath(
                createOpenGroupRequest.getBackgroundImagePath() == null ?
                    backgroundImageService.getRandomBackgroundImageUrl() :
                    createOpenGroupRequest.getBackgroundImagePath()
            )
            .description(createOpenGroupRequest.getDescription())
            .title(createOpenGroupRequest.getTitle())
            .groupType(GroupType.OPEN);

        Category category = queryGroupCategoryById(
            createOpenGroupRequest.getCategoryId());
        groupBuilder.category(category);

        Group group = groupBuilder.build();
        return group;
    }


    /**
     * 친구 그룹 생성 로직 뺀 함수입니다..
     * 썸네일, 백그라운드 서비스를 파사드로 빼고
     * 그룹 도메인으로 옮길 예정입니다.
     * @return Group
     */
    private Group makeFriendGroup() {
        //defaultCategory
        Category category = queryGroupCategoryById(1L);

        Group group = Group.builder()
            .publicAccess(false)
            .thumbnailPath(
                thumbnailImageService.getRandomThumbnailUrl()
            )
            .backgroundImagePath(
                backgroundImageService.getRandomBackgroundImageUrl()
            )
            .category(category)
            .title(Group.generateGroupTitle())
            .groupType(GroupType.FRIEND).build();

        return group;
    }


    /**
     * 그룹을 업데이트 합니다
     * @return GroupResponse
     */
    public GroupResponse updateGroup(Long groupId , UpdateGroupRequest updateGroupRequest) {
        Long reqUserId = SecurityUtils.getCurrentUserId();
        Group group = queryGroup(groupId);

        // 그룹 유저 일급 컬랙션
        GroupUsers groupUsers = group.getGroupUsers();
        // reqUser 가 호스트인지 확인하는 메서드
        groupUsers.validReqUserIsGroupHost(reqUserId);
        Category category = queryGroupCategoryById(updateGroupRequest.getCategoryId());
        group.updateGroup(updateGroupRequest.toUpdateGroupDto(), category);

        return new GroupResponse(
            group.getGroupBaseInfoVo(),
            groupUsers.getUserInfoVoList()
            , true);
    }


    /**
     * 그룹을 삭제합니다
     * @param groupId
     */
    public void deleteGroup(Long groupId) {
        Long reqUserId = SecurityUtils.getCurrentUserId();

        Group group = queryGroup(groupId);
        User reqUser = userUtils.getUserFromSecurityContext();
        GroupUsers groupUsers = group.getGroupUsers();

        groupUsers.validReqUserIsGroupHost(reqUserId);

        // 캐스케이드 타입 all로 줬습니다.!
        // 그룹지우면 그룹유저 미들 테이블 삭제됩니다.
        groupRepository.delete(group);

    }

    /**
     * id를 통해서 그룹의 디테일한 정보를 가져옵니다.
     * @return GroupResponse
     */
    public GroupResponse getGroupDetailById(Long groupId) {
        Long reqUserId = SecurityUtils.getCurrentUserId();

        Group group = queryGroup(groupId);
        GroupUsers groupUsers = group.getGroupUsers();

        Boolean iHost = groupUsers.checkReqUserGroupHost(reqUserId);

        return new GroupResponse(
            group.getGroupBaseInfoVo(),
            groupUsers.getUserInfoVoList(),
            iHost);
    }


    /**
     * 모든 그룹의 정보를 가져옵니다.
     * @return GroupBriefInfoListResponse
     */
    public GroupBriefInfoListResponse findAllOpenGroups() {
        List<Group> groupList = groupRepository.findAllByGroupType(GroupType.OPEN);

        return getGroupBriefInfoListResponse(groupList);
    }

    /**
     * 내가 들어가 있는 그룹목록을 가져옵니다.
     * @return GroupBriefInfoListResponse
     */
    public GroupBriefInfoListResponse findAllJoinedGroups() {
        User reqUser = userUtils.getUserFromSecurityContext();
        GroupUsers groupUsers = GroupUsers.from(groupUserRepository.findAllByUser(reqUser));

        List<Group> groupList = groupUsers.getGroupList();

        return getGroupBriefInfoListResponse(groupList);
    }

    /**
     * 내가 들어가 있는 그룹 목록중에서 GroupType에 따라 필터링합니다.
     * @param groupInTypeRequest
     * @return GroupBriefInfoListResponse
     */
    public GroupBriefInfoListResponse findJoinedGroupByType(GroupInTypeRequest groupInTypeRequest) {

        if(groupInTypeRequest == GroupInTypeRequest.ALL){
            return findAllJoinedGroups();
        }

        User reqUser = userUtils.getUserFromSecurityContext();
        GroupType groupType = GroupType.valueOf(groupInTypeRequest.getValue());

        GroupUsers groupUsers = GroupUsers.from(
            groupUserRepository.findJoinedGroupUserByGroupType(reqUser, groupType));
        List<Group> groupList = groupUsers.getGroupList();

        return getGroupBriefInfoListResponse(groupList);
    }

    /**
     * 탐색탭에서 카테고리별 오픈 그룹 검색
     * @return GroupBriefInfoListResponse
     */
    public GroupBriefInfoListResponse findOpenGroupByCategory(Long categoryId) {
        if (categoryId.equals(Category.defaultEmptyCategoryId)) {
            return findAllOpenGroups();
        }
        Category category = queryGroupCategoryById(categoryId);

        List<Group> groupList = groupRepository.findAllByGroupTypeAndCategory(GroupType.OPEN,category);

        return getGroupBriefInfoListResponse(groupList);
    }


    /**
     * 중복된 그룹리스트에 대한 정보를 반환하는 코드를 추출했습니다.
     * @return GroupBriefInfoListResponse
     */
    private GroupBriefInfoListResponse getGroupBriefInfoListResponse(List<Group> groupList) {
        List<GroupBriefInfoDto> groupBriefInfoDtos = groupList.stream()
            .map(group ->
                new GroupBriefInfoDto(
                    group.getGroupBaseInfoVo(),
                    group.getMemberCount()))
            .collect(Collectors.toList());

        return new GroupBriefInfoListResponse(groupBriefInfoDtos);
    }


    public GroupBriefInfoListResponse searchOpenGroups(String searchString) {

        List<Group> groupList = groupRepository.findByGroupTypeAndTitleContaining(GroupType.OPEN,
            searchString);

        return getGroupBriefInfoListResponse(groupList);
    }

    public GroupResponse addMembersToGroup(Long groupId, List<Long> requestMemberIds) {
        Long reqUserId = SecurityUtils.getCurrentUserId();

        // 이미 방안에 들어갔는지 검증
        Group group = queryGroup(groupId);
        GroupUsers groupUsers = group.getGroupUsers();
        groupUsers.validInviteUsersAlreadyEnterGroup(requestMemberIds);
        // 내 친구 목록 불러오기

        List<Long> myFriendList = relationUtils.findMyFriendUserIdList(reqUserId);

        // 그룹이 오픈 그룹이면
        // 초대 요청 보내기
        if(group.getGroupType().equals(GroupType.OPEN)){
            List<Long> sendInviteUserIds = requestMemberIds.stream()
                .filter(id -> !myFriendList.contains(id))
                .collect(Collectors.toList());
            inviteService.makeInvites(group ,sendInviteUserIds);
        }

        //요청한 목록 중에서 내 친구 인 사람
        List<Long> addMemberList = requestMemberIds.stream()
            .filter(myFriendList::contains)
            .collect(Collectors.toList());

        List<User> findUserList = userUtils.findByIdIn(addMemberList);
        // 내 친구 목록에 있는 사람들은 그냥 방안에 넣기
        groupUsers.addMembers(findUserList ,group);
        return new GroupResponse(group.getGroupBaseInfoVo(),
            groupUsers.getUserInfoVoList(),
            groupUsers.checkReqUserGroupHost(reqUserId));

    }

    public GroupResponse deleteMemberFromGroup(Long groupId, Long userId) {
        Long reqUserId = SecurityUtils.getCurrentUserId();

        Group group = queryGroup(groupId);
        GroupUsers groupUsers = group.getGroupUsers();

        // 일반 유저가 본인 스스로 방에서 나갈때
        if(reqUserId.equals(userId)){
            if(groupUsers.checkReqUserGroupHost(reqUserId))
                throw HostCanNotLeaveGroupException.EXCEPTION;
            groupUsers.removeUserByUserId(userId);
            return new GroupResponse(group.getGroupBaseInfoVo(),groupUsers.getUserInfoVoList(),false);
        }

        // 방장의 권한으로 내쫓을때
        groupUsers.validReqUserIsGroupHost(reqUserId);
        groupUsers.removeUserByUserId(userId);
        return new GroupResponse(group.getGroupBaseInfoVo(),groupUsers.getUserInfoVoList(),true);

    }
}
