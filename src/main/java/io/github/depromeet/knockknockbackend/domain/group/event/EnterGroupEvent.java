package io.github.depromeet.knockknockbackend.domain.group.event;

import lombok.Builder;
import lombok.Getter;

public class EnterGroupEvent {

    @Getter
    @Builder
    public static class InviteLink {
        private final Long groupId;
        private final Long enterUserId;
    }

    @Getter
    @Builder
    public static class HostAccept {
        private final Long enterUserId;
        private final Long hostUserId;
        private final Long groupId;
    }

    @Getter
    @Builder
    public static class MemberInvite {
        private final Long enterUserId;
        private final Long inviterId;
        private final Long groupId;
    }
}
