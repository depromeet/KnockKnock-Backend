package io.github.depromeet.knockknockbackend.domain.notification.domain;

import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import java.time.LocalDateTime;
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
}
