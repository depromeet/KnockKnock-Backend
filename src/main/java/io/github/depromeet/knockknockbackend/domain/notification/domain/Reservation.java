package io.github.depromeet.knockknockbackend.domain.notification.domain;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "tbl_reservation")
@Entity
public class Reservation extends BaseTimeEntity {

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

    public void changeSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt;
    }

    public static Reservation of(
            LocalDateTime sendAt,
            String title,
            String content,
            String imageUrl,
            Group group,
            User sendUser) {
        return Reservation.builder()
                .sendAt(sendAt)
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .group(group)
                .sendUser(sendUser)
                .build();
    }
}
