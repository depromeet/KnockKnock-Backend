package io.github.depromeet.knockknockbackend.domain.storage.domain;


import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import javax.persistence.*;
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
        name = "tbl_storage",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"notification_id", "user_id"})})
@Entity
public class Storage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "notification_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Notification notification;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public static Storage of(Notification notification, User user) {
        return Storage.builder().notification(notification).user(user).build();
    }
}
