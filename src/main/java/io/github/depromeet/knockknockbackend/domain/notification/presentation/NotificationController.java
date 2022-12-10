package io.github.depromeet.knockknockbackend.domain.notification.presentation;

import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request.RegisterFcmTokenRequest;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request.SendInstanceRequest;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryAlarmHistoryResponse;
import io.github.depromeet.knockknockbackend.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
        @PageableDefault(size = 5, sort = "sendAt", direction = Direction.DESC) Pageable pageable) {
        return notificationService.queryAlarmHistoryByUserId(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/token")
    public void registerFcmToken(@RequestBody RegisterFcmTokenRequest request) {
        notificationService.registerFcmToken(request);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/instance")
    public void sendInstance(@RequestBody SendInstanceRequest request) {
        notificationService.sendInstance(request);
    }


}
