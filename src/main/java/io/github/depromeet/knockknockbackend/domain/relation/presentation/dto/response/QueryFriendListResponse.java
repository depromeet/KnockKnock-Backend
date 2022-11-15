package io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QueryFriendListResponse {

    private final List<QueryFriendListResponseElement> friendList;

}
