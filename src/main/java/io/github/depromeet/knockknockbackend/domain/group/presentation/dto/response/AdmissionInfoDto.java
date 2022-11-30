package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response;


import io.github.depromeet.knockknockbackend.domain.group.domain.AdmissionState;
import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AdmissionInfoDto {

    private MemberInfoDto user;
    private AdmissionState admissionState;
    private Long id;

    private LocalDateTime createdAt;

    @Builder
    public AdmissionInfoDto(UserInfoVO userInfoVO, AdmissionState admissionState, Long id, LocalDateTime createdAt) {
        this.user = new MemberInfoDto(userInfoVO);
        this.admissionState = admissionState;
        this.id = id;
        this.createdAt = createdAt;
    }
}
