package io.github.depromeet.knockknockbackend.domain.report.domain;


import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.database.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "tbl_report",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"reporter_id", "notificationId"})})
@Entity
public class Report extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // notification 의 복사본 저장
    private String title;
    private String content;
    private String imageUrl;

    private Long notificationId;

    @JoinColumn(name = "reporter_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User reporter;

    @JoinColumn(name = "defendant_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User defendant;

    private String description;
    private ReportReason reportReason;

    // 처리한 관리자
    @JoinColumn(name = "manager_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User manager;

    // 처리여부
    private Boolean processed = false;

    public static Report of(
            Long reporterId,
            ReportReason reportReason,
            String description,
            Notification notification) {
        return Report.builder()
                .reportReason(reportReason)
                .description(description)
                .reporter(User.of(reporterId))
                .defendant(notification.getSendUser())
                .title(notification.getTitle())
                .content(notification.getContent())
                .notificationId(notification.getId())
                .imageUrl(notification.getImageUrl())
                .build();
    }
}
