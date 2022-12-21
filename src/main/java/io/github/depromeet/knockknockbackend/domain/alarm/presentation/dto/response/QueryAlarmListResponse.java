package io.github.depromeet.knockknockbackend.domain.alarm.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QueryAlarmListResponse {

    private final List<QueryAlarmListResponseElement> list;

}
