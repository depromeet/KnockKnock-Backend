package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class SendInstanceRequest {
    @NotNull
    private Long groupId;
    private String title;
    @NotBlank
    private String content;
    private String imageUrl;
}
