package io.github.depromeet.knockknockbackend.domain.user.domain;


import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
}
