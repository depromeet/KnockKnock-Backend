package io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class QueryFriendListResponseElement {

    private final Long id;
    private final String nickname;
    private final String profilePath;

    public QueryFriendListResponseElement(UserInfoVO infoVO) {
        this.id = infoVO.getId();
        this.nickname = infoVO.getNickname();
        this.profilePath = infoVO.getProfilePath();
    }

}
