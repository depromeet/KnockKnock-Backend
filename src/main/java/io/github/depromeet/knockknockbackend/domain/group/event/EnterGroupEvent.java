package io.github.depromeet.knockknockbackend.domain.group.event;


import io.github.depromeet.knockknockbackend.global.event.DomainEvent;
import lombok.Builder;
import lombok.Getter;

public class EnterGroupEvent {

    @Getter
    @Builder
    public static class InviteLink implements DomainEvent {
        private final Long groupId;
        private final Long enterUserId;
    }

    @Getter
    @Builder
    public static class HostAccept implements DomainEvent {
        private final Long enterUserId;
        private final Long hostUserId;
        private final Long groupId;
    }

    @Getter
    @Builder
    public static class MemberInvite implements DomainEvent {
        private Long enterUserId;
        private Long inviterId;
        private Long groupId;
    }
}
