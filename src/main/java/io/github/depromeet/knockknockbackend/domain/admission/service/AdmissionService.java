package io.github.depromeet.knockknockbackend.domain.admission.service;


import io.github.depromeet.knockknockbackend.domain.admission.domain.Admission;
import io.github.depromeet.knockknockbackend.domain.admission.domain.AdmissionState;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUsers;
import io.github.depromeet.knockknockbackend.domain.admission.domain.repository.AdmissionRepository;
import io.github.depromeet.knockknockbackend.domain.admission.exception.AdmissionNotFoundException;
import io.github.depromeet.knockknockbackend.domain.group.domain.usecase.AdmissionUsecase;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoListResponse;
import io.github.depromeet.knockknockbackend.domain.group.service.GroupUtils;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdmissionService implements AdmissionUsecase {

    private final AdmissionRepository admissionRepository;
    private final UserUtils userUtils;

    private final GroupUtils groupUtils;

    private Admission queryAdmission(Long admissionId) {
        return admissionRepository.findById(admissionId)
            .orElseThrow(()-> AdmissionNotFoundException.EXCEPTION);
    }

    private AdmissionInfoDto getAdmissionInfoDto(Admission admission) {
        return AdmissionInfoDto.builder()
            .admissionState(admission.getAdmissionState())
            .id(admission.getId())
            .userInfoVO(admission.getRequester().getUserInfo())
            .createdAt(admission.getCreatedDate())
            .build();
    }


    /**
     * 그룹 입장요청을 합니다.
     */
    @Override
    public AdmissionInfoDto requestAdmission(Long groupId) {
        User reqUser = userUtils.getUserFromSecurityContext();
        Group group = groupUtils.queryGroup(groupId);
        groupUtils.validUserIsAlreadyEnterGroup(group,reqUser.getId());

        //TODO : 요청시 알림 넣어주기?
        Admission admission = Admission.createAdmission(reqUser, group);
        admissionRepository.save(admission);

        return getAdmissionInfoDto(admission);
    }


    /**
     * 방장이 요청상태가 PENDING 인 그룹 가입 요청을 가져옵니다.
     */
    @Override
    public AdmissionInfoListResponse getAdmissions(Long groupId) {
        Long userId = SecurityUtils.getCurrentUserId();
        Group group = groupUtils.queryGroup(groupId);
        groupUtils.validReqUserIsGroupHost(group,userId);

        List<Admission> Admissions = admissionRepository.findByGroupAndAdmissionState(group ,
            AdmissionState.PENDING);

        List<AdmissionInfoDto> admissionInfoDtos = Admissions.stream().map(
                this::getAdmissionInfoDto)
            .collect(Collectors.toList());

        return new AdmissionInfoListResponse(admissionInfoDtos);
    }


    /**
     * 방장이 그룹 가입 요청을 승인합니다.
     */
    @Override
    public AdmissionInfoDto acceptAdmission(Long groupId , Long admissionId) {
        Long userId = SecurityUtils.getCurrentUserId();
        Group group = groupUtils.queryGroup(groupId);
        groupUtils.validReqUserIsGroupHost(group,userId);

        Admission admission = queryAdmission(admissionId);
        admission.acceptAdmission();

        return getAdmissionInfoDto(admission);
    }
    /**
     * 방장이 그룹 가입 요청을 거절합니다.
     */
    @Override
    public AdmissionInfoDto refuseAdmission(Long groupId , Long admissionId) {
        Long userId = SecurityUtils.getCurrentUserId();
        Group group = groupUtils.queryGroup(groupId);
        groupUtils.validReqUserIsGroupHost(group,userId);

        Admission admission = queryAdmission(admissionId);
        admission.refuseAdmission();

        return getAdmissionInfoDto(admission);
    }
}
