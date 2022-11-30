package io.github.depromeet.knockknockbackend.domain.group.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.Admission;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUsers;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.AdmissionRepository;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoListResponse;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final UserRepository userRepository;
    private final UserUtils userUtils;


    public AdmissionInfoDto requestAdmission(Group group) {
        User reqUser = userUtils.getUserFromSecurityContext();
        GroupUsers groupUsers = group.getGroupUsers();
        groupUsers.validUserIsAlreadyEnterGroup(reqUser);
        
        //TODO : 요청시 알림 넣어주기?
        Admission admission = Admission.createAdmission(reqUser, group);
        admissionRepository.save(admission);

        return AdmissionInfoDto.builder()
            .admissionState(admission.getAdmissionState())
            .id(admission.getId())
            .userInfoVO(admission.getRequester().getUserInfo())
            .createdAt(admission.getCreatedDate())
            .build();
    }

    public AdmissionInfoListResponse getAdmissions(Group group) {
    }

    public AdmissionInfoDto acceptAdmission(Group group, Long admissionId) {
    }

    public AdmissionInfoDto refuseAdmission(Group group, Long admissionId) {
    }
}
