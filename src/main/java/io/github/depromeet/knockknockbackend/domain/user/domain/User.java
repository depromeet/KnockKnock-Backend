package io.github.depromeet.knockknockbackend.domain.user.domain;

import javax.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String role ;

    @Column
    private String nickName;
    @Column
    private String oauthProvider;
    @Column
    private String oauthId;
    @Column
    private String email;

    @Builder
    public User(Long id, String nickName, String oauthProvider, String oauthId , String email) {
        this.id = id;
        this.nickName = nickName;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.email = email;
    }


}
