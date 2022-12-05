package io.github.depromeet.knockknockbackend.domain.group.service;

import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.Invite;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.InviteRepository;
import io.github.depromeet.knockknockbackend.domain.group.exception.InviteNotFoundException;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;

    private final UserUtils userUtils;

    public Invite queryInvite(Long inviteId) {
        return inviteRepository.findById(inviteId)
            .orElseThrow(() -> InviteNotFoundException.EXCEPTION);
    }

    public List<Invite> makeInvites(Group group, List<Long> requestAdmissionIds) {
        List<User> users = userUtils.findByIdIn(requestAdmissionIds);
        User reqUser = userUtils.getUserFromSecurityContext();
        //TODO : 요청시 알림 넣어주기?

        List<Invite> admissionList = users.stream()
            .map(user -> Invite.createInvite(user,reqUser, group))
            .collect(Collectors.toList());
        inviteRepository.saveAll(admissionList);

        return admissionList;
    }

    // 다른 도메인에서 호출 할 메소드들...?
    public Invite findInviteById(Long inviteId){
        return queryInvite(inviteId);
    }
    public Invite acceptInvite(Long inviteId){
        Invite invite = queryInvite(inviteId);
        invite.acceptInvite();
        return invite;
    }
    public Invite refuseInvite(Long inviteId){
        Invite invite = queryInvite(inviteId);
        invite.refuseInvite();
        return invite;
    }
}
