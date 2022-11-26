package io.github.depromeet.knockknockbackend.domain.group.domain;

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

    public void validReqUserIsHost(User reqUser) {
        groupUserList.stream()
            .filter(groupUser -> groupUser.getUser().equals(reqUser) && groupUser.getIsHost())
            .findFirst()
            .orElseThrow(() -> NotHostException.EXCEPTION);
    }
}
