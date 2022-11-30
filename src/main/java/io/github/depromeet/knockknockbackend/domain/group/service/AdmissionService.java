package io.github.depromeet.knockknockbackend.domain.group.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.Admission;
import io.github.depromeet.knockknockbackend.domain.group.domain.AdmissionState;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUsers;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.AdmissionRepository;
import io.github.depromeet.knockknockbackend.domain.group.exception.AdmissionNotFoundException;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoListResponse;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final UserUtils userUtils;
    // TODO : 친구 초대했을 때도 Admission send를 해야함!!!

    private Admission queryAdmission(Long admissionId) {
        return admissionRepository.findById(admissionId).orElseThrow(()-> AdmissionNotFoundException.EXCEPTION);
    }

    private AdmissionInfoDto getAdmissionInfoDto(Admission admission) {
        return AdmissionInfoDto.builder()
            .admissionState(admission.getAdmissionState())
            .id(admission.getId())
            .userInfoVO(admission.getRequester().getUserInfo())
            .createdAt(admission.getCreatedDate())
            .build();
    }

    public AdmissionInfoDto requestAdmission(Group group) {
        User reqUser = userUtils.getUserFromSecurityContext();
        GroupUsers groupUsers = group.getGroupUsers();
        groupUsers.validUserIsAlreadyEnterGroup(reqUser);
        
        //TODO : 요청시 알림 넣어주기?
        Admission admission = Admission.createAdmission(reqUser, group);
        admissionRepository.save(admission);

        return getAdmissionInfoDto(admission);
    }



    public AdmissionInfoListResponse getAdmissions(Group group) {

        User reqUser = userUtils.getUserFromSecurityContext();
        GroupUsers groupUsers = group.getGroupUsers();
        groupUsers.checkReqUserGroupHost(reqUser);


        List<Admission> Admissions = admissionRepository.findByGroupAndAdmissionState(group ,
            AdmissionState.PENDING);

        List<AdmissionInfoDto> admissionInfoDtos = Admissions.stream().map(
                this::getAdmissionInfoDto)
            .collect(Collectors.toList());

        return new AdmissionInfoListResponse(admissionInfoDtos);
    }

    public AdmissionInfoDto acceptAdmission(Group group , Long admissionId) {
        User reqUser = userUtils.getUserFromSecurityContext();
        // 이거 어차피 뺄거
        GroupUsers groupUsers = group.getGroupUsers();
        groupUsers.checkReqUserGroupHost(reqUser);

        //TODO : AdmissionNotFoundException
        Admission admission = queryAdmission(admissionId);
        admission.acceptAdmission();

        return getAdmissionInfoDto(admission);
    }

    public AdmissionInfoDto refuseAdmission(Group group , Long admissionId) {
        User reqUser = userUtils.getUserFromSecurityContext();
        // 이거 어차피 뺄거
        GroupUsers groupUsers = group.getGroupUsers();
        groupUsers.checkReqUserGroupHost(reqUser);

        //TODO : AdmissionNotFoundException
        Admission admission = queryAdmission(admissionId);
        admission.refuseAdmission();

        return getAdmissionInfoDto(admission);
    }


}
