package io.github.depromeet.knockknockbackend.domain.admission.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdmissionState {
    PENDING("PENDING"),
    ACCEPT("ACCEPT"),
    REFUSE("REFUSE");

    private String value;
}
