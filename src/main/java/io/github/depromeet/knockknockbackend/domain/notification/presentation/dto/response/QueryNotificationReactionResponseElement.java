package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response;


import io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationReactionCountInfoVo;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryNotificationReactionResponseElement {
    private final MyNotificationReactionResponseElement myReactionInfo;
    private final List<NotificationReactionCountInfoVo> reactionCountInfos;
}
