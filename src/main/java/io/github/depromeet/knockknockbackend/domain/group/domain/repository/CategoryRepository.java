package io.github.depromeet.knockknockbackend.domain.group.domain.repository;


import io.github.depromeet.knockknockbackend.domain.group.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(
            value =
                    "SELECT * "
                            + "FROM (SELECT g.category_id ,sum(notiGroup.notiCount) AS score "
                            + "    FROM (SELECT noti.group_id, count(*) AS notiCount "
                            + "          FROM tbl_notification AS noti "
                            + "          GROUP BY noti.group_id "
                            + "    ) AS notiGroup , tbl_group AS g "
                            + "    WHERE g.id = notiGroup.group_id And g.category_id != 1 "
                            + "    GROUP BY g.category_id "
                            + "    ORDER BY score DESC "
                            + "    LIMIT 5 "
                            + ") AS idAndScore , tbl_group_category AS c "
                            + "WHERE c.id = idAndScore.category_id ",
            nativeQuery = true)
    List<Category> findFamousCategory();
}
