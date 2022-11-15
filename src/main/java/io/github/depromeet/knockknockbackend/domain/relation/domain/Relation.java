package io.github.depromeet.knockknockbackend.domain.relation.domain;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Table(name = "tbl_relation")
@Entity
public class Relation {

    @Id
    private Long id;

    private boolean isFriend;

    @JoinColumn(name = "send_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User sendUser;

    @JoinColumn(name = "receive_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User receiveUser;

    public UserInfoVO getSendUserInfo() {
        return this.sendUser.getUserInfo();
    }

    public UserInfoVO getReceiveUserInfo() {
        return this.receiveUser.getUserInfo();
    }


}
