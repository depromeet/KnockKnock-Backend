package io.github.depromeet.knockknockbackend.domain.alarm.presentation.dto.response;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryAlarmListResponse {

    private final List<QueryAlarmListResponseElement> list;
}
