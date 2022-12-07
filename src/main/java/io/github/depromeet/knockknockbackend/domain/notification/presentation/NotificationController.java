package io.github.depromeet.knockknockbackend.domain.notification.presentation;

import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request.RegisterFcmTokenRequest;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request.SendInstanceRequest;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryAlarmHistoryResponse;
import io.github.depromeet.knockknockbackend.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@Tag(name = "알람 관련 컨트롤러", description = "")
@RequiredArgsConstructor
@RequestMapping("/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "최신 푸쉬알림 리스트")
    @GetMapping
    public QueryAlarmHistoryResponse queryAlarmHistoryByUserId(
        @PageableDefault(size = 5, sort = "sendAt", direction = Direction.DESC) Pageable pageable) {
        return notificationService.queryAlarmHistoryByUserId(pageable);
    }

    @Operation(summary = "FCM 토큰 등록")
    @PostMapping("/token")
    public ResponseEntity<Void> registerFcmToken(@RequestBody RegisterFcmTokenRequest request) {
        notificationService.registerFcmToken(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "즉시 알림 발송")
    @PostMapping("/instance")
    public ResponseEntity<Void> sendInstance(@RequestBody SendInstanceRequest request) {
        notificationService.sendInstance(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "알림방 푸쉬알림 리스트")
    @GetMapping("/{group_id}")
    public QueryAlarmHistoryResponse queryHistoryByGroupId(
        @PageableDefault(size = 20, sort = "sendAt", direction = Direction.DESC) Pageable pageable,
        @PathVariable(value = "group_id") Long groupId) {
        return notificationService.queryHistoryByGroupId(pageable, groupId);
    }


}
