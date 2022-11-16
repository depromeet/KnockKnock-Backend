package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import io.github.depromeet.knockknockbackend.domain.group.domain.BackgroundImage;
import io.github.depromeet.knockknockbackend.domain.group.domain.Thumbnail;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface BackGroundImageRepository {
    @Query(value = "SELECT * FROM tbl_group_background_image order by RAND() limit 1",nativeQuery = true)
    Optional<BackgroundImage> findRandomBackgroundImage();
}
