package io.github.depromeet.knockknockbackend.domain.notification.domain;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationBaseInfoVo;
import io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationReactionBaseInfoVo;
import io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationReactionInfoVo;
import io.github.depromeet.knockknockbackend.domain.reaction.domain.NotificationReaction;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_notification")
@Entity
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime sendAt;

    private String title;

    private String content;

    private String imageUrl;

    @JoinColumn(name = "group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @JoinColumn(name = "send_user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User sendUser;

    @JoinColumn(name = "receive_user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User receiveUser;

    @Builder.Default
    @OneToMany(mappedBy = "notification", fetch = FetchType.LAZY)
    private Set<NotificationReaction> notificationReactions = new HashSet<>();

    public static Notification of(Long notificationId) {
        return Notification.builder()
            .id(notificationId)
            .build();
    }

    public static Notification of(String title, String content, String imageUrl, Group group,
        User sendUser, LocalDateTime sendAt) {
        return Notification.builder()
            .title(title)
            .content(content)
            .imageUrl(imageUrl)
            .group(group)
            .sendUser(sendUser)
            .sendAt(sendAt)
            .build();
    }

    public NotificationBaseInfoVo getNotificationBaseInfoVo(Long userId) {
        return NotificationBaseInfoVo.builder()
            .notificationId(id)
            .title(title)
            .content(content)
            .imageUrl(imageUrl)
            .sendAt(sendAt)
            .sendUserId(sendUser.getId())
            .notificationReactionBaseInfoVo(getNotificationReactionBaseInfoVo(userId))
            .build();
    }

    private NotificationReactionBaseInfoVo getNotificationReactionBaseInfoVo(Long userId) {
        return NotificationReactionBaseInfoVo.builder()
            .myReactionId(getMyReactionId(userId))
            .reactionInfo(getNotificationReactionInfoVo())
            .build();
    }

    private List<NotificationReactionInfoVo> getNotificationReactionInfoVo() {
        Map<Long, Long> countPerReactions = notificationReactions.stream()
            .collect(
                groupingBy(notificationReaction ->
                    notificationReaction.getReaction().getId(), counting())
            );

        return countPerReactions.entrySet().stream()
            .map(countOfReactionId ->
                NotificationReactionInfoVo.builder()
                    .reactionId(countOfReactionId.getKey())
                    .reactionCount(countOfReactionId.getValue().intValue())
                    .build()
            ).collect(Collectors.toList());
    }

    private Long getMyReactionId(Long userId) {
        Optional<NotificationReaction> result = notificationReactions.stream()
            .filter(notificationReaction -> userId.equals(notificationReaction.getUser().getId()))
            .findAny();

        if(result.isPresent()){
            return result.get().getReaction().getId();
        }
        return null;
    }

}
