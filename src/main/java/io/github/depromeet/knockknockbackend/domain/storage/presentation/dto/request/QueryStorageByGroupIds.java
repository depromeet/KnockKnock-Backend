package io.github.depromeet.knockknockbackend.domain.storage.presentation.dto.request;


import java.util.List;
import lombok.Getter;

@Getter
public class QueryStorageByGroupIds {
    List<Long> groupIds;
}
