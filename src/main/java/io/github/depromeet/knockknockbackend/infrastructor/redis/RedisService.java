package io.github.depromeet.knockknockbackend.infrastructor.redis;

import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public interface RedisService {
    void setValues(String key, String data);

    void setValues(String key, String data, Duration duration);

    String getValues(String key);
    void deleteValues(String key);

    boolean hasKey(String key);
}
