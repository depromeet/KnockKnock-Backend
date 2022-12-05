package io.github.depromeet.knockknockbackend.domain.group.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InviteState {
    PENDING("PENDING"),
    ACCEPT("ACCEPT"),
    REFUSE("REFUSE");

    private String value;
}
