package io.github.depromeet.knockknockbackend.domain.reaction.service;


import io.github.depromeet.knockknockbackend.domain.asset.domain.Reaction;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.reaction.domain.NotificationReaction;
import io.github.depromeet.knockknockbackend.domain.reaction.domain.repository.NotificationReactionRepository;
import io.github.depromeet.knockknockbackend.domain.reaction.exception.ReactionAlreadyExistException;
import io.github.depromeet.knockknockbackend.domain.reaction.exception.ReactionForbiddenException;
import io.github.depromeet.knockknockbackend.domain.reaction.exception.ReactionNotExistException;
import io.github.depromeet.knockknockbackend.domain.reaction.presentation.dto.request.RegisterReactionRequest;
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
        try {
            notificationReactionRepository.save(
                    NotificationReaction.of(
                            Notification.of(request.getNotificationId()),
                            Reaction.of(request.getReactionId()),
                            userUtils.getUserFromSecurityContext()));
        } catch (Exception e) {
            throw ReactionAlreadyExistException.EXCEPTION;
        }
    }

    @Transactional
    public void changeReaction(Long notificationReactionId, RegisterReactionRequest request) {
        NotificationReaction notificationReaction =
                queryNotificationReaction(notificationReactionId);
        validateMyReactionTheNotification(notificationReaction);

        notificationReaction.changeReaction(Reaction.of(request.getReactionId()));
    }

    @Transactional
    public void deleteReaction(Long notificationReactionId) {
        NotificationReaction notificationReaction =
                queryNotificationReaction(notificationReactionId);
        validateMyReactionTheNotification(notificationReaction);
        notificationReactionRepository.deleteById(notificationReactionId);
    }

    private void validateMyReactionTheNotification(NotificationReaction notificationReaction) {
        if (!notificationReaction.getUserId().equals(SecurityUtils.getCurrentUserId())) {
            throw ReactionForbiddenException.EXCEPTION;
        }
    }

    private NotificationReaction queryNotificationReaction(Long notificationReactionId) {
        return notificationReactionRepository
                .findById(notificationReactionId)
                .orElseThrow(() -> ReactionNotExistException.EXCEPTION);
    }
}
