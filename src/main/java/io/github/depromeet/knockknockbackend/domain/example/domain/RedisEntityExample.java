package io.github.depromeet.knockknockbackend.domain.example.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash
public class RedisEntityExample {

    @Id // springframework.data의 annotation인점 주의
    private String id;

    @Indexed private String refreshToken; // Indexed가 없으면 Id를 제외한 field로 search가 불가능함.

    @TimeToLive // TTL
    private Long ttl;

    public void updateTTL(Long ttl) {
        this.ttl += ttl;
    }
}
