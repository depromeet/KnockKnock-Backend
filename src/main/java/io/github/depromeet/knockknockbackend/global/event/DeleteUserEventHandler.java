package io.github.depromeet.knockknockbackend.global.event;


import io.github.depromeet.knockknockbackend.domain.credential.event.DeleteUserEvent;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupRepository;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteUserEventHandler {

    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;

    @Async
    @TransactionalEventListener(
            classes = DeleteUserEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    public void handleDeleteUserEvent(DeleteUserEvent deleteUserEvent) {
        // TODO : // 기획 맞춰서 삭제하기
        Long userId = deleteUserEvent.getUserId();
        log.info(userId.toString() + "회원탈퇴");
    }
}
