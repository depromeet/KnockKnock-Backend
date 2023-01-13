package io.github.depromeet.knockknockbackend.domain.reaction.domain;


import io.github.depromeet.knockknockbackend.domain.asset.domain.Reaction;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "tbl_notification_reaction",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"notification_id", "user_id"})})
@Entity
public class NotificationReaction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "notification_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Notification notification;

    @JoinColumn(name = "reaction_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Reaction reaction;

    @JoinColumn(name = "user_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Long getUserId() {
        return user.getId();
    }

    public static NotificationReaction of(Long id, Reaction reaction) {
        return NotificationReaction.builder().id(id).reaction(reaction).build();
    }

    public static NotificationReaction of(Notification notification, Reaction reaction, User user) {
        return NotificationReaction.builder()
                .notification(notification)
                .reaction(reaction)
                .user(user)
                .build();
    }
}
