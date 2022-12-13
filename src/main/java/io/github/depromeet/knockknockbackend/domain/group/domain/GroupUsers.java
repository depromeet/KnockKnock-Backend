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

    public static GroupUsers createGroupUsers(User host , List<User>  requestUserList , Group group){
        List<GroupUser> requestGroupUserList = requestUserList.stream()
            .map(user -> GroupUser.builder()
                .isHost(false)
                .user(user)
                .group(group).build())
            .collect(Collectors.toList());
        GroupUser hostMember = GroupUser.builder()
            .isHost(true)
            .user(host)
            .group(group).build();
        requestGroupUserList.add(hostMember);
        return new GroupUsers(requestGroupUserList);
    }

    public List<UserInfoVO> getUserInfoVoList(){
        return groupUserList.stream().map(GroupUser::getMemberUserInfo)
            .collect(Collectors.toList());
    }

    public void validReqUserIsGroupHost(User reqUser) {
        groupUserList.stream()
            .filter(groupUser -> groupUser.getUser().equals(reqUser) && groupUser.getIsHost())
            .findFirst()
            .orElseThrow(() -> NotHostException.EXCEPTION);
    }

    public Boolean checkReqUserGroupHost(User reqUser) {
        return groupUserList.stream()
            .anyMatch(groupUser ->
                groupUser.getIsHost() && groupUser.getUser().getId().equals(reqUser.getId()));

    }

    protected int getMemberCount(){
        return groupUserList.size();
    }

    public List<Group> getGroupList(){
        return groupUserList.stream().map(GroupUser::getGroup).collect(
            Collectors.toList());
    }


    public void validUserIsAlreadyEnterGroup(User reqUser){
        if(checkUserIsAlreadyEnterGroup(reqUser))
            throw AlreadyGroupEnterException.EXCEPTION;
    }

    public boolean checkUserIsAlreadyEnterGroup(User reqUser) {
        return groupUserList.stream()
            .anyMatch(groupUser ->
                groupUser.getUser().getId().equals(reqUser.getId()));
    }


    public List<Long> getUserIds() {
        return groupUserList.stream()
            .map(groupUser -> groupUser.getUser().getId())
            .collect(Collectors.toList());
    }

    public void addMembers(List<User> newMembers , Group group){

        List<GroupUser> newGroupUsers = newMembers.stream()
            .map(user -> new GroupUser(group, user, false))
            .collect(Collectors.toList());
        groupUserList.addAll(newGroupUsers);
    }

    public void removeUserByUserId(Long userId){
        groupUserList.removeIf(groupUser -> groupUser.getUserId().equals(userId));
    }

    public void addMember(User reqUser, Group group) {
        GroupUser groupUser = new GroupUser(group, reqUser, false);
        groupUserList.add(groupUser);
    }
}
