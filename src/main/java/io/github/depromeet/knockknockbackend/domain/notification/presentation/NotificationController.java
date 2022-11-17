package io.github.depromeet.knockknockbackend.domain.notification.presentation;

import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryAlarmHistoryResponse;
import io.github.depromeet.knockknockbackend.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@Tag(name = "알람 관련 컨트롤러", description = "")
@RequiredArgsConstructor
@RequestMapping("/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/history")
    public QueryAlarmHistoryResponse queryAlarmHistoryByUserId(
        @RequestParam("page") Optional<Integer> page,
        @RequestParam("size") Optional<Integer> size) {
        return notificationService.queryAlarmHistoryByUserId(page.orElse(Notification.DEFAULT_PAGE),
            size.orElse(Notification.DEFAULT_PAGE_SIZE));
    }
}
