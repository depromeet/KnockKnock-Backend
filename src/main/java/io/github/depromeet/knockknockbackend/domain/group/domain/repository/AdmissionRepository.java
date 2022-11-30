package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import io.github.depromeet.knockknockbackend.domain.group.domain.Admission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionRepository  extends JpaRepository<Admission, Long> {

}
