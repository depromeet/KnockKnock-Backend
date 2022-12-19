package io.github.depromeet.knockknockbackend.domain.notification.domain;


import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_notification_experience")
@Entity
public class NotificationExperience extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime sendAt;

    private String title;

    private String content;

    private String imageUrl;

    public static NotificationExperience of(
            String token, LocalDateTime sendAt, String title, String content, String imageUrl) {
        return NotificationExperience.builder()
                .token(token)
                .sendAt(sendAt)
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }
}
