package io.github.depromeet.knockknockbackend.domain.notification.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupUserRepository;
import io.github.depromeet.knockknockbackend.domain.group.exception.NotMemberException;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.GroupInfoForNotificationDto;
import io.github.depromeet.knockknockbackend.domain.group.service.GroupService;
import io.github.depromeet.knockknockbackend.domain.notification.domain.*;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.DeviceTokenRepository;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.NotificationExperienceRepository;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.NotificationRepository;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.ReservationRepository;
import io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationReactionCountInfoVo;
import io.github.depromeet.knockknockbackend.domain.notification.exception.*;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request.*;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.*;
import io.github.depromeet.knockknockbackend.domain.reaction.domain.NotificationReaction;
import io.github.depromeet.knockknockbackend.domain.reaction.domain.repository.NotificationReactionRepository;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import io.github.depromeet.knockknockbackend.infrastructor.fcm.FcmService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService implements NotificationUtils {

    private static final boolean CREATED_DELETED_STATUS = false;
    private final EntityManager entityManager;
    private final FcmService fcmService;
    private final GroupService groupService;
    private final NotificationRepository notificationRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final NotificationReactionRepository notificationReactionRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationExperienceRepository notificationExperienceRepository;
    private final GroupUserRepository groupUserRepository;

    @Transactional(readOnly = true)
    public QueryNotificationListLatestResponse queryListLatest() {
        List<Notification> notifications =
                notificationRepository.findSliceLatestByReceiver(SecurityUtils.getCurrentUserId());

        List<NotificationReaction> myNotificationReactions = retrieveMyReactions(notifications);
        List<QueryNotificationListResponseElement> notificationListResponseElements =
                notifications.stream()
                        .map(
                                notification ->
                                        getQueryNotificationListResponseElements(
                                                notification, myNotificationReactions))
                        .collect(Collectors.toList());

        return new QueryNotificationListLatestResponse(notificationListResponseElements);
    }

    @Transactional(readOnly = true)
    public QueryNotificationListResponse queryListByGroupId(Pageable pageable, Long groupId) {

        Slice<Notification> notifications =
                notificationRepository.findSliceByGroupId(
                        SecurityUtils.getCurrentUserId(),
                        groupId,
                        CREATED_DELETED_STATUS,
                        pageable);

        List<NotificationReaction> myNotificationReactions =
                retrieveMyReactions(notifications.getContent());
        Slice<QueryNotificationListResponseElement> queryNotificationListResponseElements =
                notifications.map(
                        notification ->
                                getQueryNotificationListResponseElements(
                                        notification, myNotificationReactions));

        QueryReservationListResponseElement queryReservationListResponseElement =
                reservationRepository
                        .findByGroupAndSendUser(
                                Group.of(groupId), User.of(SecurityUtils.getCurrentUserId()))
                        .map(
                                reservation ->
                                        QueryReservationListResponseElement.builder()
                                                .reservationId(reservation.getId())
                                                .title(reservation.getTitle())
                                                .content(reservation.getContent())
                                                .imageUrl(reservation.getImageUrl())
                                                .sendAt(reservation.getSendAt())
                                                .build())
                        .orElse(null);

        return new QueryNotificationListResponse(
                queryReservationListResponseElement, queryNotificationListResponseElements);
    }

    public QueryNotificationListResponseElement getQueryNotificationListResponseElements(
            Notification notification, List<NotificationReaction> notificationReactions) {

        MyNotificationReactionResponseElement myNotificationReactionResponseElement =
                notificationReactions.stream()
                        .filter(
                                notificationReaction ->
                                        notification.equals(notificationReaction.getNotification()))
                        .findAny()
                        .map(
                                notificationReaction ->
                                        MyNotificationReactionResponseElement.builder()
                                                .notificationReactionId(
                                                        notificationReaction.getId())
                                                .reactionId(
                                                        notificationReaction.getReaction().getId())
                                                .build())
                        .orElse(null);

        List<NotificationReactionCountInfoVo> notificationReactionCountInfoVo =
                notificationReactionRepository.findAllCountByNotification(notification);

        QueryNotificationReactionResponseElement notificationReactionResponseElement =
                QueryNotificationReactionResponseElement.builder()
                        .myReactionInfo(myNotificationReactionResponseElement)
                        .reactionCountInfos(notificationReactionCountInfoVo)
                        .build();

        return QueryNotificationListResponseElement.builder()
                .notificationId(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .imageUrl(notification.getImageUrl())
                .createdDate(notification.getCreatedDate())
                .sendUserId(notification.getSendUser().getId())
                .groups(
                        new GroupInfoForNotificationDto(
                                notification.getGroup().getGroupBaseInfoVo()))
                .reactions(notificationReactionResponseElement)
                .build();
    }

    @Transactional
    public void registerFcmToken(RegisterFcmTokenRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Optional<DeviceToken> deviceTokenOptional =
                deviceTokenRepository.findByDeviceId(request.getDeviceId());

        deviceTokenOptional.ifPresentOrElse(
                deviceToken -> {
                    if (deviceToken.getUserId().equals(currentUserId)) {
                        deviceTokenRepository.save(deviceToken.changeToken(request.getToken()));
                    } else {
                        deviceTokenRepository.deleteById(deviceToken.getId());
                        entityManager.flush();
                        deviceTokenRepository.save(
                                DeviceToken.of(
                                        currentUserId, request.getDeviceId(), request.getToken()));
                    }
                },
                () ->
                        deviceTokenRepository.save(
                                DeviceToken.of(
                                        currentUserId, request.getDeviceId(), request.getToken())));
    }

    @Transactional
    public void sendInstance(SendInstanceRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        validateSendNotificationPermission(request.getGroupId(), currentUserId);

        List<DeviceToken> deviceTokens = getDeviceTokens(request.getGroupId(), currentUserId);
        List<String> tokens = getFcmTokens(deviceTokens);
        if (tokens.isEmpty()) {
            return;
        }

        fcmService.sendGroupMessage(
                tokens, request.getTitle(), request.getContent(), request.getImageUrl());

        recordNotification(
                deviceTokens,
                request.getTitle(),
                request.getContent(),
                request.getImageUrl(),
                Group.of(request.getGroupId()),
                User.of(currentUserId),
                null);
    }

    public void sendInstanceToMeBeforeSignUp(SendInstanceToMeBeforeSignUpRequest request) {
        fcmService.sendMessage(request.getToken(), request.getContent());
        notificationExperienceRepository.save(
                NotificationExperience.of(request.getToken(), request.getContent()));
    }

    public void deleteByNotificationId(Long notificationId) {
        Notification notification = queryNotificationById(notificationId);
        validateDeletePermission(notification);
        notification.deleteNotification();
        notificationRepository.save(notification);
    }

    public List<DeviceToken> getDeviceTokens(Long groupId, Long sendUserId) {
        Boolean nightOption = null;
        if (NightCondition.isNight()) {
            nightOption = true;
        }

        return notificationRepository.findTokenByGroupAndOptionAndNonBlock(
                sendUserId, groupId, nightOption);
    }

    public List<String> getFcmTokens(List<DeviceToken> deviceTokens) {
        return deviceTokens.stream().map(DeviceToken::getToken).collect(Collectors.toList());
    }

    public void recordNotification(
            List<DeviceToken> deviceTokens,
            String title,
            String content,
            String imageUrl,
            Group group,
            User sendUser,
            LocalDateTime createdDate) {
        Notification notification =
                Notification.makeNotificationWithReceivers(
                        deviceTokens, title, content, imageUrl, group, sendUser, createdDate);
        notificationRepository.save(notification);
    }

    public List<NotificationReaction> retrieveMyReactions(List<Notification> notifications) {
        return notificationReactionRepository.findByUserAndNotificationIn(
                User.of(SecurityUtils.getCurrentUserId()), notifications);
    }

    private void validateDeletePermission(Notification notification) {
        if (!SecurityUtils.getCurrentUserId().equals(notification.getSendUser().getId())) {
            throw NotificationForbiddenException.EXCEPTION;
        }
    }

    public void validateSendNotificationPermission(Long groupId, Long userId) {
        Group group = groupService.queryGroup(groupId);
        // 홀로외침방이면 방장인지
        if (GroupType.OPEN.equals(group.getGroupType())) group.validUserIsHost(userId);
        // 친구방이면 그룹 소속원인지
        if (GroupType.FRIEND.equals(group.getGroupType())) {
            // group.validUserIsMemberOfGroup(userId); //todo: 해당 부분 호춣시 불필요한 쿼리 발생.
            groupUserRepository
                    .findByGroupAndUser(group, User.of(userId))
                    .orElseThrow(() -> NotMemberException.EXCEPTION);
        }
    }

    @Override
    public Notification queryNotificationById(Long notificationId) {
        return notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> NotificationNotFoundException.EXCEPTION);
    }
}
