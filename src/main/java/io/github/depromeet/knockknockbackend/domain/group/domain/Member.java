package io.github.depromeet.knockknockbackend.domain.group.domain;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Table(name = "tbl_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group; //그룹 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //유저

    private Boolean isHost;
    // user , host (방장) true false 나중에 어떻게될지 모르니 role로 냅뒀음!

    public UserInfoVO getMemberUserInfo() {
        return this.user.getUserInfo();
    }
    @Builder
    public Member(Group group, User user, Boolean isHost) {
        this.group = group;
        this.user = user;
        this.isHost = isHost;
    }

    public static List<Member> makeGroupsMemberList(User host , List<User>  requestUserList , Group group){
        List<Member> memberList = requestUserList.stream()
            .map(user -> Member.builder()
                .isHost(false)
                .user(user)
                .group(group).build())
            .collect(Collectors.toList());
        Member hostMember = Member.builder()
            .isHost(true)
            .user(host)
            .group(group).build();
        memberList.add(hostMember);
        return memberList;
    }
}
