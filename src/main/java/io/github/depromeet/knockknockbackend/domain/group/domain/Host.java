package io.github.depromeet.knockknockbackend.domain.group.domain;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Host {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private User user;

    public Host(User host) {
        this.user = host;
    }

    public static Host from(User host){
        return new Host(host);
    }

    public UserInfoVO getUserInfo() {
        return user.getUserInfo();
    }

    public Long getId() {
        return user.getId();
    }
}
