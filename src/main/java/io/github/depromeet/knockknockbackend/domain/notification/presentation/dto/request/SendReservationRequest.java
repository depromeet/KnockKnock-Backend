package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SendReservationRequest {
    @NotNull private Long groupId;
    private String title;
    @NotBlank private String content;
    private String imageUrl;
    @Future @NotNull @JsonFormat private LocalDateTime sendAt;
}
