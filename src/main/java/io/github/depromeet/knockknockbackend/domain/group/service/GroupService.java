package io.github.depromeet.knockknockbackend.domain.group.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.Category;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group.GroupBuilder;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUser;
import io.github.depromeet.knockknockbackend.domain.group.domain.InviteTokenRedisEntity;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.CategoryRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupUserRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.InviteTokenRedisEntityRepository;
import io.github.depromeet.knockknockbackend.domain.group.exception.CategoryNotFoundException;
import io.github.depromeet.knockknockbackend.domain.group.exception.GroupNotFoundException;
import io.github.depromeet.knockknockbackend.domain.group.exception.InvalidInviteTokenException;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateFriendGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateOpenGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.GroupInTypeRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.UpdateGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupBriefInfoDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupBriefInfos;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupInviteLinkResponse;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupResponse;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.exception.UserNotFoundException;
import io.github.depromeet.knockknockbackend.global.utils.generate.TokenGenerator;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService implements GroupUtils {

    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;
    private final CategoryRepository categoryRepository;
    private final AssetUtils assetUtils;
    private final UserUtils userUtils;
    private final TokenGenerator tokenGenerator;

    private final InviteTokenRedisEntityRepository inviteTokenRedisEntityRepository;

    /**
     * 카테고리 정보를 가져옵니다.
     *
     * @param categoryId 카테고리 아이디
     * @throws CategoryNotFoundException
     */
    private Category queryGroupCategoryById(Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> CategoryNotFoundException.EXCEPTION);
    }

    /**
     * 그룹 정보를 가져옵니다.
     *
     * @param groupId 그룹 아이디
     * @return
     */
    @Override
    public Group queryGroup(Long groupId) {
        return groupRepository
                .findById(groupId)
                .orElseThrow(() -> GroupNotFoundException.EXCEPTION);
    }

    /**
     * 방에 집어넣을 유저가 존재하는 유저인지 판단합니다.
     *
     * @param findUserList
     * @param requestUserIdList
     * @throws UserNotFoundException
     */
    private void validReqMemberNotExist(List<User> findUserList, List<Long> requestUserIdList) {
        // 요청한 유저중에 없는 유저가 있으면 안됩니다.
        if (requestUserIdList.size() != findUserList.size()) {
            throw UserNotFoundException.EXCEPTION;
        }
    }

    /**
     * 오픈 그룹(홀로 외침방 )을 생성합니다.
     *
     * @return CreateGroupResponse
     */
    @Transactional
    public GroupResponse createOpenGroup(CreateOpenGroupRequest createOpenGroupRequest) {
        // 요청자 정보 시큐리티에서 가져옴
        User currentUser = userUtils.getUserFromSecurityContext();

        List<Long> memberIds = createOpenGroupRequest.getMemberIds();
        // 요청받은 id 목록들로 디비에서 조회
        List<User> members = userUtils.findByIdIn(memberIds);

        // 요청받은 유저 아이디 목록이 디비에 존재하는 지 확인
        validReqMemberNotExist(members, createOpenGroupRequest.getMemberIds());
        // 오픈 그룹 만들기
        Group group = makeOpenGroup(createOpenGroupRequest, currentUser);
        // 그룹 유저 리스트 추가
        groupRepository.save(group);

        group.memberInviteNewUsers(currentUser.getId(), members);

        return getGroupResponse(group, currentUser.getId());
    }

    /**
     * 친구그룹을 만듭니다
     *
     * @return CreateGroupResponse
     */
    @Transactional
    public GroupResponse createFriendGroup(CreateFriendGroupRequest createFriendGroupRequest) {
        User currentUser = userUtils.getUserFromSecurityContext();

        // TODO : 요청 받은 memeberId가 친구 목록에 속해있는지 검증.
        List<Long> memberIds = createFriendGroupRequest.getMemberIds();
        // 요청받은 id 목록들로 디비에서 조회
        List<User> members = userUtils.findByIdIn(memberIds);
        // 그룹 만들기
        Group group = makeFriendGroup(currentUser, memberIds.size());
        // 그룹 유저 리스트만들기
        groupRepository.save(group);

        group.memberInviteNewUsers(currentUser.getId(), members);

        return getGroupResponse(group, currentUser.getId());
    }

    /**
     * 오픈 그룹 생성 로직 뺀 함수입니다.. 썸네일, 백그라운드 서비스를 파사드로 빼고 그룹 도메인으로 옮길 예정입니다.
     *
     * @return Group
     */
    private Group makeOpenGroup(CreateOpenGroupRequest createOpenGroupRequest, User host) {
        GroupBuilder groupBuilder =
                Group.builder()
                        .publicAccess(createOpenGroupRequest.getPublicAccess())
                        .thumbnailPath(
                                StringUtils.hasText(createOpenGroupRequest.getThumbnailPath())
                                        ? createOpenGroupRequest.getThumbnailPath()
                                        : assetUtils.getRandomThumbnailUrl())
                        .backgroundImagePath(
                                StringUtils.hasText(createOpenGroupRequest.getBackgroundImagePath())
                                        ? createOpenGroupRequest.getBackgroundImagePath()
                                        : assetUtils.getRandomBackgroundImageUrl())
                        .description(createOpenGroupRequest.getDescription())
                        .title(createOpenGroupRequest.getTitle())
                        .groupType(GroupType.OPEN)
                        .host(host);

        Category category = queryGroupCategoryById(createOpenGroupRequest.getCategoryId());
        groupBuilder.category(category);

        return groupBuilder.build();
    }

    /**
     * 친구 그룹 생성 로직 뺀 함수입니다.. 썸네일, 백그라운드 서비스를 파사드로 빼고 그룹 도메인으로 옮길 예정입니다.
     *
     * @return Group
     */
    private Group makeFriendGroup(User host, Integer memberCount) {
        // defaultCategory
        Category category = queryGroupCategoryById(Category.defaultEmptyCategoryId);

        return Group.builder()
                .publicAccess(false)
                .thumbnailPath(assetUtils.getRandomThumbnailUrl())
                .backgroundImagePath(assetUtils.getRandomBackgroundImageUrl())
                .category(category)
                .title(Group.generateGroupTitle(host.getNickname(), memberCount))
                .groupType(GroupType.FRIEND)
                .host(host)
                .build();
    }

    /**
     * 그룹을 업데이트 합니다
     *
     * @return GroupResponse
     */
    @Transactional
    public GroupResponse updateGroup(Long groupId, UpdateGroupRequest updateGroupRequest) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Group group = queryGroup(groupId);
        group.validUserIsHost(currentUserId);

        Category category = queryGroupCategoryById(updateGroupRequest.getCategoryId());
        group.updateGroup(updateGroupRequest.toUpdateGroupDto(), category);

        return getGroupResponse(group, currentUserId);
    }

    /**
     * 그룹을 삭제합니다
     *
     * @param groupId
     */
    @Transactional
    public void deleteGroup(Long groupId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Group group = queryGroup(groupId);
        group.validUserIsHost(currentUserId);

        groupRepository.delete(group);
    }

    /**
     * id를 통해서 그룹의 디테일한 정보를 가져옵니다.
     *
     * @return GroupResponse
     */
    public GroupResponse getGroupDetailById(Long groupId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Group group = queryGroup(groupId);

        return getGroupResponse(group, currentUserId);
    }

    /**
     * 모든 그룹의 정보를 가져옵니다.
     *
     * @return GroupBriefInfoListResponse
     */
    public Slice<Group> findSliceOpenGroups(PageRequest pageRequest) {
        Slice<Group> groupList = groupRepository.findSliceByGroupType(GroupType.OPEN, pageRequest);

        return groupList;
    }

    /**
     * 내가 들어가 있는 그룹목록을 가져옵니다.
     *
     * @return GroupBriefInfoListResponse
     */
    public Slice<GroupBriefInfoDto> findAllJoinedGroups(PageRequest pageRequest) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Slice<GroupUser> sliceGroupUsers =
                groupUserRepository.findJoinedGroupUser(currentUserId, pageRequest);

        return sliceGroupUsers.map(
                groupUser -> {
                    Group group = groupUser.getGroup();
                    return new GroupBriefInfoDto(
                            group.getGroupBaseInfoVo(), group.getMemberCount());
                });
    }

    /**
     * 내가 들어가 있는 그룹 목록중에서 GroupType에 따라 필터링합니다.
     *
     * @param groupInTypeRequest
     * @return GroupBriefInfoListResponse
     */
    public Slice<GroupBriefInfoDto> findJoinedGroupByType(
            GroupInTypeRequest groupInTypeRequest, PageRequest pageRequest) {

        if (groupInTypeRequest == GroupInTypeRequest.ALL) {
            return findAllJoinedGroups(pageRequest);
        }

        Long currentUserId = SecurityUtils.getCurrentUserId();
        GroupType groupType = GroupType.valueOf(groupInTypeRequest.getValue());

        Slice<GroupUser> sliceGroupUsers =
                groupUserRepository.findJoinedGroupUserByGroupType(
                        currentUserId, groupType, pageRequest);

        return sliceGroupUsers.map(
                groupUser -> {
                    Group group = groupUser.getGroup();
                    return new GroupBriefInfoDto(
                            group.getGroupBaseInfoVo(), group.getMemberCount());
                });
    }

    /**
     * 탐색탭에서 카테고리별 오픈 그룹 검색
     *
     * @return GroupBriefInfoListResponse
     */
    public Slice<GroupBriefInfoDto> findOpenGroupByCategory(
            Long categoryId, PageRequest pageRequest) {
        if (categoryId.equals(Category.defaultEmptyCategoryId)) {
            Slice<Group> allOpenGroups = findSliceOpenGroups(pageRequest);
            return allOpenGroups.map(
                    group ->
                            new GroupBriefInfoDto(
                                    group.getGroupBaseInfoVo(), group.getMemberCount()));
        }
        Category category = queryGroupCategoryById(categoryId);

        Slice<Group> groupList =
                groupRepository.findSliceByGroupTypeAndCategory(
                        GroupType.OPEN, category, pageRequest);

        return groupList.map(
                group -> new GroupBriefInfoDto(group.getGroupBaseInfoVo(), group.getMemberCount()));
    }

    public Slice<GroupBriefInfoDto> searchOpenGroups(String searchString, PageRequest pageRequest) {

        Slice<Group> groupList =
                groupRepository.findByGroupTypeAndTitleContaining(
                        GroupType.OPEN, searchString, pageRequest);

        return groupList.map(
                group -> new GroupBriefInfoDto(group.getGroupBaseInfoVo(), group.getMemberCount()));
    }

    @Transactional
    public GroupResponse addMembersToGroup(Long groupId, List<Long> requestMemberIds) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Group group = queryGroup(groupId);

        List<User> findUserList = userUtils.findByIdIn(requestMemberIds);
        // 요청받은 유저 아이디 목록이 디비에 존재하는 지 확인
        validReqMemberNotExist(findUserList, requestMemberIds);
        // TODO : 친구 목록에 존재하는지 확인
        group.memberInviteNewUsers(currentUserId, findUserList);

        return getGroupResponse(group, currentUserId);
    }

    @Override
    @Transactional
    public void acceptMemberToGroup(Group group, User newUser) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        group.hostAcceptMember(currentUserId, newUser);
    }

    @Transactional
    public GroupResponse leaveFromGroup(Long groupId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Group group = queryGroup(groupId);

        group.removeMemberByUserId(currentUserId);

        return getGroupResponse(group, currentUserId);
    }

    @Transactional
    public GroupInviteLinkResponse createGroupInviteLink(Long groupId) {
        Group group = queryGroup(groupId);
        Long currentUserId = SecurityUtils.getCurrentUserId();

        // 요청한 유저가 멤버가 아니라면 링크 발급이 안되어야 한다.
        group.validUserIsMemberOfGroup(currentUserId);

        String token = tokenGenerator.nextString();

        InviteTokenRedisEntity tokenRedisEntity =
                InviteTokenRedisEntity.builder()
                        .token(token)
                        .groupId(groupId)
                        .issuerId(currentUserId)
                        .ttl(24L)
                        .build();

        inviteTokenRedisEntityRepository.save(tokenRedisEntity);

        return GroupInviteLinkResponse.from(token);
    }

    @Transactional
    public GroupResponse checkGroupInviteLink(Long groupId, String token) {
        InviteTokenRedisEntity inviteToken =
                inviteTokenRedisEntityRepository
                        .findById(token)
                        .orElseThrow(() -> InvalidInviteTokenException.EXCEPTION);

        if (!inviteToken.getGroupId().equals(groupId)) {
            throw InvalidInviteTokenException.EXCEPTION;
        }

        Group group = queryGroup(groupId);
        User currentUser = userUtils.getUserFromSecurityContext();
        Long currentUserId = currentUser.getId();

        group.enterGroup(currentUser);

        return getGroupResponse(group, currentUserId);
    }

    @Transactional
    public GroupResponse deleteMemberFromGroup(Long groupId, Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Group group = queryGroup(groupId);

        group.kickUserFromGroup(currentUserId, userId);

        return getGroupResponse(group, currentUserId);
    }

    private GroupResponse getGroupResponse(Group group, Long currentUserId) {
        return new GroupResponse(
                group.getGroupBaseInfoVo(),
                group.getMemberInfoVOs(),
                group.checkUserIsHost(currentUserId));
    }

    @Cacheable(cacheNames = "famousGroup", cacheManager = "redisCacheManager")
    public GroupBriefInfos getFamousGroup() {
        List<GroupBriefInfoDto> collect =
                groupRepository.findFamousGroup().stream()
                        .map(
                                group ->
                                        new GroupBriefInfoDto(
                                                group.getGroupBaseInfoVo(), group.getMemberCount()))
                        .collect(Collectors.toList());

        return new GroupBriefInfos(collect);
    }
}
