package io.github.depromeet.knockknockbackend.domain.option.domain.repository;


import io.github.depromeet.knockknockbackend.domain.option.domain.Option;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface OptionRepository extends CrudRepository<Option, Long> {

    Optional<Option> findByUserId(Long currentUserId);
}
