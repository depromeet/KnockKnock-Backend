package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GroupInTypeRequest {
    ALL("ALL"),
    FRIEND("FRIEND"),
    OPEN("OPEN");

    private String value;
}
