package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ChangeSendAtReservationRequest {
    private Long reservationId;
    @JsonFormat private LocalDateTime sendAt;
}
