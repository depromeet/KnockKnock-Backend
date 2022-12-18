package io.github.depromeet.knockknockbackend.domain.notification.domain;


import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_notification_receiver")
@Entity
public class NotificationReceiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "notification_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Notification notification;

    @JoinColumn(name = "receiver_user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User receiver;

    public NotificationReceiver(Notification notification, User receiver) {
        this.notification = notification;
        this.receiver = receiver;
    }

}
