package io.github.depromeet.knockknockbackend.domain.notification.presentation;


import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request.*;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryNotificationListLatestResponse;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryNotificationListResponse;
import io.github.depromeet.knockknockbackend.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "access-token")
@Tag(name = "푸쉬알림 관련 컨트롤러", description = "")
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "최신 푸쉬알림 리스트")
    @GetMapping
    public QueryNotificationListLatestResponse queryListLatest() {
        return notificationService.queryListLatest();
    }

    @Operation(summary = "FCM 토큰 등록")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/token")
    public void registerFcmToken(@RequestBody RegisterFcmTokenRequest request) {
        notificationService.registerFcmToken(request);
    }

    @Operation(summary = "즉시 푸쉬알림 발송")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void sendInstance(@RequestBody SendInstanceRequest request) {
        notificationService.sendInstance(request);
    }

    @Operation(summary = "예약 푸쉬알림 발송")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservation")
    public void sendReservation(@RequestBody SendReservationRequest request) {
        notificationService.sendReservation(request);
    }

    @Operation(summary = "알림방 푸쉬알림 리스트")
    @GetMapping("/{group_id}")
    public QueryNotificationListResponse queryListByGroupId(
            @PageableDefault(size = 20, sort = "createdDate", direction = Direction.DESC)
                    Pageable pageable,
            @PathVariable(value = "group_id") Long groupId) {
        return notificationService.queryListByGroupId(pageable, groupId);
    }

    @Operation(summary = "푸쉬알림 삭제")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{notification_id}")
    public void deleteByNotificationId(
            @PathVariable(value = "notification_id") Long notificationId) {
        notificationService.deleteByNotificationId(notificationId);
    }

    @Operation(summary = "똑똑 미리체험하기 : 회원가입 전 자신에게 푸쉬알림 보내기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/experience")
    public void sendInstanceToMeBeforeSignUp(
            @RequestBody SendInstanceToMeBeforeSignUpRequest request) {
        notificationService.sendInstanceToMeBeforeSignUp(request);
    }

    @Operation(summary = "예약 푸쉬알림 시간수정")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/reservation")
    public void changeSendAtReservation(@RequestBody ChangeSendAtReservationRequest request) {
        notificationService.changeSendAtReservation(request);
    }
}
