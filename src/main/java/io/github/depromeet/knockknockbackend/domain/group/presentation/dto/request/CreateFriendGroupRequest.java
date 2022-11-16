package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;

import java.util.ArrayList;
import java.util.List;

public class CreateFriendGroupRequest {

    // 친구목록만 받아옴 빈리스트도 가능
    private List<Long> memberIds = new ArrayList<>();

}
