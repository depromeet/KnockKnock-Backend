package io.github.depromeet.knockknockbackend.domain.storage.presentation.dto.request;


import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DeleteStorage {
    @Size(min = 1)
    @NotNull
    List<Long> storageIds;
}
