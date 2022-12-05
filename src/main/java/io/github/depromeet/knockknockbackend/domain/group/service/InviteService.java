package io.github.depromeet.knockknockbackend.domain.group.service;

import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.InviteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;

    public void makeInvites(Group group, List<Long> sendInviteUserIds, Long reqUserId) {
    }
}
