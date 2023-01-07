package io.github.depromeet.knockknockbackend.domain.alarm.domain;


import io.github.depromeet.knockknockbackend.domain.alarm.domain.types.AlarmType;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Getter
@NoArgsConstructor
@Table(name = "tbl_alarm")
@Entity
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "send_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User sendUser;

    @JoinColumn(name = "receive_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User receiveUser;

    @Enumerated(EnumType.STRING)
    private AlarmType type;

    @Column(length = 30)
    private String title;

    private String content;

    private boolean isActivate;

    @CreatedDate private LocalDateTime createdAt;

    @Builder
    public Alarm(User sendUser, User receiveUser, AlarmType type, String title, String content) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
        this.type = type;
        this.title = title;
        this.content = content;
        this.isActivate = true;
    }

    public Long getSendUserId() {
        return this.sendUser.getId();
    }

    public String getSendUserProfile() {
        return this.sendUser.getProfilePath();
    }
}
