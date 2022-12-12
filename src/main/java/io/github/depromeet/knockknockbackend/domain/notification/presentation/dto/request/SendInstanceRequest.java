package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;

import lombok.Getter;

@Getter
public class SendInstanceRequest {

    private Long groupId;
    private String title;
    private String content;
    private String imageUrl;

}
