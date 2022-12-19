package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response;


import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryReservationListResponseElement {
    private Long reservationId;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime sendAt;
}
