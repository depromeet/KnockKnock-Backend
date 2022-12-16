package io.github.depromeet.knockknockbackend.domain.storage.presentation;

import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryNotificationListInStorageResponse;
import io.github.depromeet.knockknockbackend.domain.storage.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@Tag(name = "보관함 관련 컨트롤러", description = "")
@RequiredArgsConstructor
@RequestMapping("/storages")
@RestController
public class StorageController {

    private final StorageService storageService;

    @Operation(summary = "보관함 푸쉬알림 리스트 조회")
    @GetMapping
    public QueryNotificationListInStorageResponse queryNotificationsInStorage(@RequestParam(value = "groupId", required = false) Long groupId,
        @PageableDefault(size = 20, sort = "createdDate", direction = Direction.DESC) Pageable pageable) {
        return storageService.queryNotificationsInStorage(groupId, pageable);
    }

    @Operation(summary = "보관함에 푸쉬알림 보관")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{notification_id}")
    public void saveNotificationToStorage(@PathVariable("notification_id") Long notificationId) {
        storageService.saveNotificationToStorage(notificationId);
    }

    @Operation(summary = "보관함에 저장한 푸쉬알림 삭제")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{storage_id}")
    public void deleteNotificationFromStorage(@PathVariable("storage_id") Long storageId) {
        storageService.deleteNotificationFromStorage(storageId);
    }

}
