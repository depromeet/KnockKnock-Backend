package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SendReservationRequest {
    private Long groupId;
    private String title;
    private String content;
    private String imageUrl;
    @JsonFormat private LocalDateTime sendAt;
}
