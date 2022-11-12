package io.github.depromeet.knockknockbackend.domain.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 10)
    private String nickname;

    private String oauthProvider;

    private String oauthId;

    private String email;

    private String profilePath;

    @Builder
    public User(String nickname, String oauthProvider, String oauthId , String email) {
        this.nickname = nickname;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.email = email;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

}
