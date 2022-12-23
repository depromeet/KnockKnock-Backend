package io.github.depromeet.knockknockbackend.domain.notification.domain;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.reaction.domain.NotificationReaction;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
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

    private String title;

    private String content;

    private String imageUrl;

    @JoinColumn(name = "group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @JoinColumn(name = "send_user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User sendUser;

    @Builder.Default
    @OneToMany(mappedBy = "notification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NotificationReceiver> receivers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "notification", fetch = FetchType.LAZY)
    private Set<NotificationReaction> notificationReactions = new HashSet<>();

    private LocalDateTime reservedAt;

    private boolean deleted;

    private void addReceivers(List<DeviceToken> deviceTokens) {
        this.receivers.addAll(
                deviceTokens.stream()
                        .map(
                                deviceToken ->
                                        new NotificationReceiver(
                                                this,
                                                User.of(deviceToken.getUserId()),
                                                deviceToken.getToken()))
                        .collect(Collectors.toList()));
    }

    public void deleteNotification() {
        this.deleted = true;
    }

    public static Notification of(Long notificationId) {
        return Notification.builder().id(notificationId).build();
    }

    public static Notification makeNotificationWithReceivers(
            List<DeviceToken> deviceTokens,
            String title,
            String content,
            String imageUrl,
            Group group,
            User sendUser,
            LocalDateTime reservedAt) {
        Notification notification =
                Notification.builder()
                        .title(title)
                        .content(content)
                        .imageUrl(imageUrl)
                        .group(group)
                        .sendUser(sendUser)
                        .reservedAt(reservedAt)
                        .build();
        notification.addReceivers(deviceTokens);
        return notification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification that = (Notification) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
