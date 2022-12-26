package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response;


import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupBriefInfoDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryNotificationListResponseElement {
    private Long notificationId;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdDate;
    private Long sendUserId;
    private GroupBriefInfoDto groups;
    private QueryNotificationReactionResponseElement reactions;
}
