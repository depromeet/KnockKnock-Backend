package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import io.github.depromeet.knockknockbackend.domain.asset.domain.Thumbnail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThumbnailRepository  extends JpaRepository<Thumbnail, Long> {


    @Query(value = "SELECT * FROM tbl_group_thumbnail order by RAND() limit 1",nativeQuery = true)
    Optional<Thumbnail> findRandomThumbnail();
}
