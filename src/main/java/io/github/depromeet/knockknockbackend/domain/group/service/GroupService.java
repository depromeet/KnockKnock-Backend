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
     * ???????????? ????????? ???????????????.
     *
     * @param categoryId ???????????? ?????????
     * @throws CategoryNotFoundException
     */
    private Category queryGroupCategoryById(Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> CategoryNotFoundException.EXCEPTION);
    }

    /**
     * ?????? ????????? ???????????????.
     *
     * @param groupId ?????? ?????????
     * @return
     */
    @Override
    public Group queryGroup(Long groupId) {
        return groupRepository
                .findById(groupId)
                .orElseThrow(() -> GroupNotFoundException.EXCEPTION);
    }

    /**
     * ?????? ???????????? ????????? ???????????? ???????????? ???????????????.
     *
     * @param findUserList
     * @param requestUserIdList
     * @throws UserNotFoundException
     */
    private void validReqMemberNotExist(List<User> findUserList, List<Long> requestUserIdList) {
        // ????????? ???????????? ?????? ????????? ????????? ????????????.
        if (requestUserIdList.size() != findUserList.size()) {
            throw UserNotFoundException.EXCEPTION;
        }
    }

    /**
     * ?????? ??????(?????? ????????? )??? ???????????????.
     *
     * @return CreateGroupResponse
     */
    @Transactional
    public GroupResponse createOpenGroup(CreateOpenGroupRequest createOpenGroupRequest) {
        // ????????? ?????? ?????????????????? ?????????
        User currentUser = userUtils.getUserFromSecurityContext();

        List<Long> memberIds = createOpenGroupRequest.getMemberIds();
        // ???????????? id ???????????? ???????????? ??????
        List<User> members = userUtils.findByIdIn(memberIds);

        // ???????????? ?????? ????????? ????????? ????????? ???????????? ??? ??????
        validReqMemberNotExist(members, createOpenGroupRequest.getMemberIds());
        // ?????? ?????? ?????????
        Group group = makeOpenGroup(createOpenGroupRequest, currentUser);
        // ?????? ?????? ????????? ??????
        groupRepository.save(group);

        group.memberInviteNewUsers(currentUser.getId(), members);

        return getGroupResponse(group, currentUser.getId());
    }

    /**
     * ??????????????? ????????????
     *
     * @return CreateGroupResponse
     */
    @Transactional
    public GroupResponse createFriendGroup(CreateFriendGroupRequest createFriendGroupRequest) {
        User currentUser = userUtils.getUserFromSecurityContext();

        // TODO : ?????? ?????? memeberId??? ?????? ????????? ??????????????? ??????.
        List<Long> memberIds = createFriendGroupRequest.getMemberIds();
        // ???????????? id ???????????? ???????????? ??????
        List<User> members = userUtils.findByIdIn(memberIds);
        // ?????? ?????????
        Group group = makeFriendGroup(currentUser, memberIds.size());
        // ?????? ?????? ??????????????????
        groupRepository.save(group);

        group.memberInviteNewUsers(currentUser.getId(), members);

        return getGroupResponse(group, currentUser.getId());
    }

    /**
     * ?????? ?????? ?????? ?????? ??? ???????????????.. ?????????, ??????????????? ???????????? ???????????? ?????? ?????? ??????????????? ?????? ???????????????.
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
     * ?????? ?????? ?????? ?????? ??? ???????????????.. ?????????, ??????????????? ???????????? ???????????? ?????? ?????? ??????????????? ?????? ???????????????.
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
     * ????????? ???????????? ?????????
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
     * ????????? ???????????????
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
     * id??? ????????? ????????? ???????????? ????????? ???????????????.
     *
     * @return GroupResponse
     */
    public GroupResponse getGroupDetailById(Long groupId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Group group = queryGroup(groupId);

        return getGroupResponse(group, currentUserId);
    }

    /**
     * ?????? ????????? ????????? ???????????????.
     *
     * @return GroupBriefInfoListResponse
     */
    public Slice<Group> findSliceOpenGroups(PageRequest pageRequest) {
        Slice<Group> groupList = groupRepository.findSliceByGroupType(GroupType.OPEN, pageRequest);

        return groupList;
    }

    /**
     * ?????? ????????? ?????? ??????????????? ???????????????.
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
     * ?????? ????????? ?????? ?????? ??????????????? GroupType??? ?????? ??????????????????.
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
     * ??????????????? ??????????????? ?????? ?????? ??????
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
        // ???????????? ?????? ????????? ????????? ????????? ???????????? ??? ??????
        validReqMemberNotExist(findUserList, requestMemberIds);
        // TODO : ?????? ????????? ??????????????? ??????
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

        // ????????? ????????? ????????? ???????????? ?????? ????????? ???????????? ??????.
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
