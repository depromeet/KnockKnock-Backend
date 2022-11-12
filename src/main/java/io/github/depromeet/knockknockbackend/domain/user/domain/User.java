package io.github.depromeet.knockknockbackend.domain.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Table(name = "tbl_user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


}
