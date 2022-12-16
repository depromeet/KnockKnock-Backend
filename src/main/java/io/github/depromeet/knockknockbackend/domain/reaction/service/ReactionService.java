package io.github.depromeet.knockknockbackend.domain.reaction.service;

import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.reaction.domain.NotificationReaction;
import io.github.depromeet.knockknockbackend.domain.asset.domain.Reaction;
import io.github.depromeet.knockknockbackend.domain.reaction.exception.ReactionForbiddenException;
import io.github.depromeet.knockknockbackend.domain.reaction.presentation.dto.request.RegisterReactionRequest;
import io.github.depromeet.knockknockbackend.domain.reaction.domain.repository.NotificationReactionRepository;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReactionService {

    private final NotificationReactionRepository notificationReactionRepository;
    private final UserUtils userUtils;

    @Transactional
    public void registerReaction(RegisterReactionRequest request) {
        notificationReactionRepository.save(
            NotificationReaction.of(
                Notification.of(request.getNotificationId()),
                Reaction.of(request.getReactionId()),
                userUtils.getUserFromSecurityContext()
            ));
    }

    public void changeReaction(RegisterReactionRequest request) {
        if (request.getNotificationReactionId() != null) {
            validateMyReactionTheNotification(request.getNotificationReactionId());
        }

        notificationReactionRepository.save(
            NotificationReaction.of(
                request.getNotificationReactionId(),
                Reaction.of(request.getReactionId())
            ));
    }

    @Transactional
    public void deleteReaction(Long notificationReactionId) {
        validateMyReactionTheNotification(notificationReactionId);
        notificationReactionRepository.deleteById(notificationReactionId);
    }

    private void validateMyReactionTheNotification(Long notificationReactionId) {
        notificationReactionRepository.findById(notificationReactionId)
            .ifPresent(notificationReaction -> {
                if (!notificationReaction.getUser().getId().equals(SecurityUtils.getCurrentUserId()))
                    throw ReactionForbiddenException.EXCEPTION;
            });
    }

}
