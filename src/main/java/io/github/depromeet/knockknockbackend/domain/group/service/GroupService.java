package io.github.depromeet.knockknockbackend.domain.group.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group.GroupBuilder;
import io.github.depromeet.knockknockbackend.domain.group.domain.Category;
import io.github.depromeet.knockknockbackend.domain.group.domain.Member;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupCategoryRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.MemberRepository;
import io.github.depromeet.knockknockbackend.domain.group.exception.CategoryNotFoundException;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request.CreateOpenGroupRequest;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.CreateOpenGroupResponse;
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
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final GroupCategoryRepository groupCategoryRepository;

    private User getUserFromSecurityContext(){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
            .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        return user;
    }

    private Category queryGroupCategroyById(Long categoryId){
        return groupCategoryRepository.findById(categoryId)
            .orElseThrow(() -> CategoryNotFoundException.EXCEPTION);
    }

    public CreateOpenGroupResponse createOpenGroup(CreateOpenGroupRequest createOpenGroupRequest) {
        // 요청자 정보 시큐리티에서 가져옴
        User reqUser = getUserFromSecurityContext();

        createOpenGroupRequest.getMemberIds().removeIf(id -> reqUser.getId().equals(id));
        //요청받은 id 목록들로 디비에서 조회
        List<User> requestUserList =
            userRepository.findByIdIn(createOpenGroupRequest.getMemberIds());

        List<Long> requestUserIdList = requestUserList.stream().map(user -> user.getId())
            .collect(Collectors.toList());
        //요청한 유저중에 없는 유저가 있으면 안됩니다.
        if(!createOpenGroupRequest.getMemberIds().equals(requestUserIdList)){
            throw UserNotFoundException.EXCEPTION;
        }

        GroupBuilder groupBuilder = Group.builder()
            .publicAccess(createOpenGroupRequest.getPublicAccess())
            .thumbnailPath(createOpenGroupRequest.getThumbnailPath())
            .backgroundImagePath(createOpenGroupRequest.getBackgroundImagePath())
            .description(createOpenGroupRequest.getDescription())
            .title(createOpenGroupRequest.getTitle());

        if(createOpenGroupRequest.getCategoryId() != null){
            Category category = queryGroupCategroyById(
                createOpenGroupRequest.getCategoryId());
            groupBuilder.category(category);

        }

        Group group = groupBuilder.build();

        groupRepository.save(group);

        List<Member> memberList = Member.makeGroupsMemberList(reqUser, requestUserList, group);

        memberRepository.saveAll(memberList);
        // 그룹에 멤버들 저장
        group.setMembers(memberList);

        return new CreateOpenGroupResponse(group,true);
    }
}
