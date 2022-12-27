package io.github.depromeet.knockknockbackend.global.event;


import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.DeviceTokenRepository;
import io.github.depromeet.knockknockbackend.domain.notification.event.DeviceTokenEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class DeviceTokenServiceEventHandler {

    private final DeviceTokenRepository deviceTokenRepository;

    @TransactionalEventListener(
            classes = DeviceTokenEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void deleteFcmToken(DeviceTokenEvent event) {
        deviceTokenRepository.deleteByUser(event.getUser());
    }
}
