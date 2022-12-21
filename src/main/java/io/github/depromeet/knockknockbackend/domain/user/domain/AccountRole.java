package io.github.depromeet.knockknockbackend.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountRole {

    USER("USER"),
    ADMIN("ADMIN");

    private String value;

}
