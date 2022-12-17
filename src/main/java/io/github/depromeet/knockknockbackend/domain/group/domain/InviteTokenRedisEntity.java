package io.github.depromeet.knockknockbackend.domain.group.domain;


import java.util.concurrent.TimeUnit;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "inviteToken")
@Getter
public class InviteTokenRedisEntity {

    @Id private String token;

    @Indexed private Long groupId;

    @Indexed private Long issuerId;

    @TimeToLive(unit = TimeUnit.HOURS) // TTL
    private Long ttl;

    @Builder
    public InviteTokenRedisEntity(Long groupId, String token, Long issuerId, Long ttl) {
        this.groupId = groupId;
        this.issuerId = issuerId;
        this.token = token;
        this.ttl = ttl;
    }
}
