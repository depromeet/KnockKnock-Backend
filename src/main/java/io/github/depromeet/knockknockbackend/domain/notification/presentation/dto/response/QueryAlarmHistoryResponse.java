package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class QueryAlarmHistoryResponse {

    private final List<QueryAlarmHistoryResponseElement> alarmHistory;

}
