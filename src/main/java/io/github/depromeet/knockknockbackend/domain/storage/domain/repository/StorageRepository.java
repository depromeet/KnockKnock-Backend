package io.github.depromeet.knockknockbackend.domain.storage.domain.repository;


import io.github.depromeet.knockknockbackend.domain.storage.domain.Storage;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StorageRepository extends CrudRepository<Storage, Long> {

    List<Storage> findByIdIn(List<Long> storageIds);

    @Modifying
    @Query("delete from Storage s where s.id in :storageIds")
    void deleteByIdIn(@Param("storageIds") List<Long> storageIds);
}
