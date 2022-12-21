package io.github.depromeet.knockknockbackend.domain.alarm.domain.repository;

import io.github.depromeet.knockknockbackend.domain.alarm.domain.Alarm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlarmRepository extends CrudRepository<Alarm, Long> {
    List<Alarm> findByReceiveUserIdAndIsActivateIsTrue(Long userId);
}
