package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupInviteLinkResponse {

    private String link;

    public static GroupInviteLinkResponse from(String link) {
        return new GroupInviteLinkResponse(link);
    }
}
