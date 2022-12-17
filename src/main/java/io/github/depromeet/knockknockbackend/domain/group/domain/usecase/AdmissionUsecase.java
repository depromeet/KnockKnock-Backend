package io.github.depromeet.knockknockbackend.domain.group.domain.usecase;


import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoListResponse;

public interface AdmissionUsecase {

    AdmissionInfoDto requestAdmission(Long groupId);

    AdmissionInfoDto acceptAdmission(Long groupId, Long admissionId);

    AdmissionInfoDto refuseAdmission(Long groupId, Long admissionId);

    AdmissionInfoListResponse getAdmissions(Long groupId);
}
