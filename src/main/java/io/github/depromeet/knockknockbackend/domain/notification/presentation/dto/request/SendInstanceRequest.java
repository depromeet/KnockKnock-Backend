package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SendInstanceRequest {
    @NotNull private Long groupId;
    private String title;
    @NotBlank private String content;
    private String imageUrl;
}
