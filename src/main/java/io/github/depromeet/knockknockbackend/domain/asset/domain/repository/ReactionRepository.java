package io.github.depromeet.knockknockbackend.domain.asset.domain.repository;

import io.github.depromeet.knockknockbackend.domain.asset.domain.ProfileImage;
import io.github.depromeet.knockknockbackend.domain.asset.domain.Reaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    List<Reaction> findAllByOrderByListOrderAsc();

}
