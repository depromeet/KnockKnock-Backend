package io.github.depromeet.knockknockbackend.domain.asset.domain.repository;


import io.github.depromeet.knockknockbackend.domain.asset.domain.ProfileImage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    @Query(value = "SELECT * FROM tbl_profile_image order by RAND() limit 1", nativeQuery = true)
    Optional<ProfileImage> findRandomProfileImage();

    List<ProfileImage> findAllByOrderByListOrderAsc();
}
