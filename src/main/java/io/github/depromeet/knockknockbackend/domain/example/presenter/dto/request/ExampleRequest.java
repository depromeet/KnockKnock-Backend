package io.github.depromeet.knockknockbackend.domain.example.presenter.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExampleRequest {

    @NotNull
    private String exampleValue;

}
