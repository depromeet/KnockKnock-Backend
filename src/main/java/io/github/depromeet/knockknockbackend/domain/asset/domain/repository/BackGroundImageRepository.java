package io.github.depromeet.knockknockbackend.domain.asset.domain.repository;


import io.github.depromeet.knockknockbackend.domain.asset.domain.BackgroundImage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BackGroundImageRepository extends JpaRepository<BackgroundImage, Long> {
    @Query(
            value = "SELECT * FROM tbl_group_background_image order by RAND() limit 1",
            nativeQuery = true)
    Optional<BackgroundImage> findRandomBackgroundImage();

    List<BackgroundImage> findAllByOrderByListOrderAsc();
}
