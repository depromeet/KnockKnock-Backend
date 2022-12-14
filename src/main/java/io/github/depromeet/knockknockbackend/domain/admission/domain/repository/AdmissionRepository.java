package io.github.depromeet.knockknockbackend.domain.admission.domain.repository;


import io.github.depromeet.knockknockbackend.domain.admission.domain.Admission;
import io.github.depromeet.knockknockbackend.domain.admission.domain.AdmissionState;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    List<Admission> findByGroupAndAdmissionState(Group group, AdmissionState pending);
}
