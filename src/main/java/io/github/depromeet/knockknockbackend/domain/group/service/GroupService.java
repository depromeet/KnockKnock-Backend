package io.github.depromeet.knockknockbackend.domain.group.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group.GroupBuilder;
import io.github.depromeet.knockknockbackend.domain.group.domain.Category;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUser;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUsers;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupCategoryRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.MemberRepository;
import io.github.depromeet.knockknockbackend.domain.group.exception.CategoryNotFoundException;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateFriendGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateOpenGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.CreateGroupResponse;
import io.github.depromeet.knockknockbackend.domain.user.UserUtils;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.global.exception.UserNotFoundException;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
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
    private final MemberRepository memberRepository;
    private final GroupCategoryRepository groupCategoryRepository;

    private final ThumbnailImageService thumbnailImageService;
    private final BackgroundImageService backgroundImageService;

    private final UserUtils userUtils;

    private User getUserFromSecurityContext(){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userUtils.getUserById(currentUserId);
        return user;
    }

    private Category queryGroupCategoryById(Long categoryId){
        return groupCategoryRepository.findById(categoryId)
            .orElseThrow(() -> CategoryNotFoundException.EXCEPTION);
    }

    private static void checkReqMemberNotExist(List<User> findUserList, List<Long> requestUserIdList
    ) {
        List<Long> findUserIdList = findUserList.stream().map(user -> user.getId())
            .collect(Collectors.toList());
        //요청한 유저중에 없는 유저가 있으면 안됩니다.
        if(!requestUserIdList.equals(findUserIdList)){
            throw UserNotFoundException.EXCEPTION;
        }
    }

    public CreateGroupResponse createOpenGroup(CreateOpenGroupRequest createOpenGroupRequest) {
        // 요청자 정보 시큐리티에서 가져옴
        User reqUser = getUserFromSecurityContext();
        // 혹시 본인 아이디 멤버로 넣었으면 지워버리기.
        createOpenGroupRequest.getMemberIds().removeIf(id -> reqUser.getId().equals(id));
        //요청받은 id 목록들로 디비에서 조회
        List<User> findUserList = userUtils.findByIdIn(createOpenGroupRequest.getMemberIds());

        // 요청받은 유저 아이디 목록이 디비에 존재하는 지 확인
        checkReqMemberNotExist(findUserList, createOpenGroupRequest.getMemberIds());
        // 오픈 그룹 만들기
        Group group = makeOpenGroup(createOpenGroupRequest);
        groupRepository.save(group);
        // 그룹 유저 리스트 추가
        GroupUsers groupUsers = GroupUsers.createGroupUsers(reqUser, findUserList, group);
        memberRepository.saveAll(groupUsers.getGroupUserList());

        return new CreateGroupResponse(
            group.getGroupBaseInfoVo() ,
            groupUsers.getUserInfoVoList()
            ,true);
    }


    public CreateGroupResponse createFriendGroup(CreateFriendGroupRequest createFriendGroupRequest) {
        User reqUser = getUserFromSecurityContext();

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

        memberRepository.saveAll(groupUsers.getGroupUserList());


        return new CreateGroupResponse(
            group.getGroupBaseInfoVo() ,
            groupUsers.getUserInfoVoList()
            ,true);
    }

    private Group makeOpenGroup(CreateOpenGroupRequest createOpenGroupRequest) {
        GroupBuilder groupBuilder = Group.builder()
            .publicAccess(createOpenGroupRequest.getPublicAccess())
            .thumbnailPath(
                thumbnailImageService.getThumbnailUrl(createOpenGroupRequest.getThumbnailPath())
            )
            .backgroundImagePath(
                backgroundImageService.getBackgroundImageUrl(createOpenGroupRequest.getBackgroundImagePath())
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

    private Group makeFriendGroup() {
        //defaultCategory
        Category category = queryGroupCategoryById(1L);

        Group group = Group.builder()
            .publicAccess(false)
            .thumbnailPath(
                thumbnailImageService.getThumbnailUrl()
            )
            .backgroundImagePath(
                backgroundImageService.getBackgroundImageUrl()
            )
            .category(category)
            .title(Group.generateGroupTitle())
            .groupType(GroupType.FRIEND).build();

        return group;
    }
}
