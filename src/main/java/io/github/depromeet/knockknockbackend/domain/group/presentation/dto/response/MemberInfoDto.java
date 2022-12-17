package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response;


import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import lombok.Getter;

@Getter
public class MemberInfoDto {

    private Long userId;
    private String nickName;

    private String profilePath;

    public MemberInfoDto(UserInfoVO userInfoVO) {
        userId = userInfoVO.getId();
        nickName = userInfoVO.getNickname();
        profilePath = userInfoVO.getProfilePath();
    }
}
