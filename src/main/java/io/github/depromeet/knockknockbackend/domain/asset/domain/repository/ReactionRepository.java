package io.github.depromeet.knockknockbackend.domain.asset.domain.repository;

import io.github.depromeet.knockknockbackend.domain.asset.domain.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

}
