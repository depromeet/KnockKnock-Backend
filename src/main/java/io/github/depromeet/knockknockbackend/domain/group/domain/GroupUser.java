package io.github.depromeet.knockknockbackend.domain.group.domain;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
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
@Table(name = "tbl_group_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GroupUser extends BaseTimeEntity {


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

    public UserInfoVO getMemberUserInfo() {
        return this.user.getUserInfo();
    }

    public Long getUserId(){
        return user.getId();
    }
    @Builder
    public GroupUser(Group group, User user, Boolean isHost) {
        this.group = group;
        this.user = user;
        this.isHost = isHost;
    }


}
