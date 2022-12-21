package io.github.depromeet.knockknockbackend.domain.alarm.domain.repository;


import io.github.depromeet.knockknockbackend.domain.alarm.domain.Alarm;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AlarmRepository extends CrudRepository<Alarm, Long> {
    List<Alarm> findByReceiveUserIdAndIsActivateIsTrue(Long userId);
}
