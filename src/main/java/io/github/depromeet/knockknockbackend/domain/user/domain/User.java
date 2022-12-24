package io.github.depromeet.knockknockbackend.domain.user.domain;


import io.github.depromeet.knockknockbackend.domain.credential.event.DeleteUserEvent;
import io.github.depromeet.knockknockbackend.domain.credential.event.LogoutUserEvent;
import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import io.github.depromeet.knockknockbackend.domain.user.event.RegisterUserEvent;
import io.github.depromeet.knockknockbackend.global.event.Events;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    private String nickname;

    private String oauthProvider;

    private String oauthId;

    private String email;

    private String profilePath;

    @Enumerated(EnumType.STRING)
    private AccountState accountState = AccountState.NORMAL;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole = AccountRole.USER;

    @Builder
    public User(
            Long id,
            String nickname,
            String oauthProvider,
            String oauthId,
            String email,
            String profilePath) {
        this.id = id;
        this.nickname = nickname;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.email = email;
        this.profilePath = profilePath;
    }

    public UserInfoVO getUserInfo() {
        return new UserInfoVO(id, nickname, profilePath, email);
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public static User of(Long userId) {
        return User.builder().id(userId).build();
    }

    public void changeProfile(String nickname, String profilePath) {
        this.nickname = nickname;
        this.profilePath = profilePath;
    }

    public void softDeleteUser() {
        String deletedPrefix = "Deleted:";
        this.nickname = "탈퇴한 유저";
        this.oauthId = deletedPrefix + this.oauthId;
        this.email = deletedPrefix + this.email;
        this.accountState = AccountState.DELETED;
        DeleteUserEvent deleteUserEvent = DeleteUserEvent.builder().userId(this.id).build();
        Events.raise(deleteUserEvent);
    }

    @PostPersist
    public void registerEvent() {
        RegisterUserEvent registerUserEvent =
                RegisterUserEvent.builder()
                        .userInfoVO(getUserInfo())
                        .provider(this.oauthProvider)
                        .build();
        Events.raise(registerUserEvent);
    }

    public void logout() {
        LogoutUserEvent logoutUserEvent = LogoutUserEvent.builder().userId(this.id).build();
        Events.raise(logoutUserEvent);
    }
}
