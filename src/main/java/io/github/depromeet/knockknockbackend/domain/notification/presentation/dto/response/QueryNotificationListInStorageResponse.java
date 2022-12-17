package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor
public class QueryNotificationListInStorageResponse {

    private final Slice<QueryNotificationListResponseElement> notifications;
}
