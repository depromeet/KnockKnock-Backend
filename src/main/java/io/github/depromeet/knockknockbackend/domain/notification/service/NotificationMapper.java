package io.github.depromeet.knockknockbackend.domain.notification.service;

import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryAlarmHistoryResponseElement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface NotificationMapper {

    @Mapping(source = "sendUser.nickname", target = "sendUserNickname")
    QueryAlarmHistoryResponseElement toDtoForQueryAlarmHistory(Notification notification);

}
