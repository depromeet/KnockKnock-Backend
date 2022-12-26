package io.github.depromeet.knockknockbackend.domain.user.event;


import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import io.github.depromeet.knockknockbackend.global.event.DomainEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterUserEvent implements DomainEvent {
    private final UserInfoVO userInfoVO;
    private final String provider;
}
