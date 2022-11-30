package io.github.depromeet.knockknockbackend.domain.group.facade;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoDto;
import io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response.AdmissionInfoListResponse;
import io.github.depromeet.knockknockbackend.domain.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class AdmissionFacade {

    private final AdmissionService admissionService;
    private final GroupService groupService;


    public AdmissionInfoDto requestAdmission(Long groupId) {
        Group group = groupService.queryGroup(groupId);
        return admissionService.requestAdmission(group);
    }

    public AdmissionInfoListResponse getAdmissions(Long groupId) {
        Group group = groupService.queryGroup(groupId);
        return admissionService.getAdmissions(group);
    }

    public AdmissionInfoDto acceptAdmission(Long groupId, Long admissionId) {
        Group group = groupService.queryGroup(groupId);
        return admissionService.acceptAdmission(group,admissionId);
    }

    public AdmissionInfoDto refuseAdmission(Long groupId, Long admissionId) {
        Group group = groupService.queryGroup(groupId);
        return admissionService.refuseAdmission(group,admissionId);
    }
}
