package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response;


import io.github.depromeet.knockknockbackend.domain.group.domain.vo.GroupBaseInfoVo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor
public class QueryNotificationListResponse {

    private final GroupBaseInfoVo groups;
    private final List<QueryReservationListResponseElement> reservations;
    private final Slice<QueryNotificationListResponseElement> notifications;
}
