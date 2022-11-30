package io.github.depromeet.knockknockbackend.domain.group.facade;


import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class AdmissionFacade {

    public AdmissionInfoDto requestAdmission(Long groupId) {
    }

    public AdmissionInfoListResponse getAdmissions(Long groupId) {
    }

    public AdmissionInfoDto acceptAdmission(Long groupId, Long admissionId) {
    }

    public AdmissionInfoDto refuseAdmission(Long groupId, Long admissionId) {
    }
}
