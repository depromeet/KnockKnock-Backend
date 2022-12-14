package io.github.depromeet.knockknockbackend.domain.group.domain;

import io.github.depromeet.knockknockbackend.domain.group.exception.AlreadyGroupEnterException;
import io.github.depromeet.knockknockbackend.domain.group.exception.NotHostException;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupUsers {
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupUser> groupUserList = new ArrayList<>();

    public GroupUsers(List<GroupUser> groupUserList) {
        this.groupUserList = groupUserList;
    }

    public static GroupUsers from(List<GroupUser> groupUserList) {
        return new GroupUsers(groupUserList);
    }

    public static GroupUsers createGroupUsers(List<User> requestUserList ,Group group){
        List<GroupUser> requestGroupUserList = requestUserList.stream()
            .map(user -> GroupUser.builder()
                .user(user)
                .group(group).build())
            .collect(Collectors.toList());
        return new GroupUsers(requestGroupUserList);
    }

    protected List<UserInfoVO> getUserInfoVOs(){
        return groupUserList.stream().map(GroupUser::getMemberUserInfo)
            .collect(Collectors.toList());
    }

    protected int getMemberCount(){
        return groupUserList.size();
    }

    protected void validUserIsAlreadyEnterGroup(Long userId){
        if(checkUserIsAlreadyEnterGroup(userId))
            throw AlreadyGroupEnterException.EXCEPTION;
    }

    protected boolean checkUserIsAlreadyEnterGroup(Long userId) {
        return groupUserList.stream()
            .anyMatch(groupUser ->
                groupUser.getUser().getId().equals(userId));
    }

    public void addMembers(List<User> newMembers , Group group){

        List<GroupUser> newGroupUsers = newMembers.stream()
            .map(user -> new GroupUser(group, user))
            .collect(Collectors.toList());
        groupUserList.addAll(newGroupUsers);
    }

    public void removeUserByUserId(Long userId){
        groupUserList.removeIf(groupUser -> groupUser.getUserId().equals(userId));
    }

    public void addMember(User reqUser, Group group) {
        GroupUser groupUser = new GroupUser(group, reqUser);
        groupUserList.add(groupUser);
    }
}
