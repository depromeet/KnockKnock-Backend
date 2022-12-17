package io.github.depromeet.knockknockbackend.domain.asset.domain.repository;

import io.github.depromeet.knockknockbackend.domain.asset.domain.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppVersionRepository  extends JpaRepository<AppVersion, Long> {

}
