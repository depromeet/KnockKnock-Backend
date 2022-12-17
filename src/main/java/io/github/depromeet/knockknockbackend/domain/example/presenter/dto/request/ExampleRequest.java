package io.github.depromeet.knockknockbackend.domain.example.presenter.dto.request;


import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExampleRequest {

    @NotNull private String exampleValue;
}
