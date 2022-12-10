package io.github.depromeet.knockknockbackend.domain.recommendmessage.domain.repository;

import io.github.depromeet.knockknockbackend.domain.recommendmessage.domain.RecommendMessage;
import org.springframework.data.repository.CrudRepository;

public interface RecommendMessageRepository extends CrudRepository<RecommendMessage, Long> {

    RecommendMessage findAllByUseYn(boolean useYn);

}
