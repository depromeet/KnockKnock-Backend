package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupBriefInfos {

    List<GroupBriefInfoDto> groupBriefInfos;
}
