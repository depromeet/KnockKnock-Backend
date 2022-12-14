package io.github.depromeet.knockknockbackend.domain.notification.domain;


import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
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

    private String content;

    public static NotificationExperience of(String token, String content) {
        return NotificationExperience.builder().token(token).content(content).build();
    }
}
