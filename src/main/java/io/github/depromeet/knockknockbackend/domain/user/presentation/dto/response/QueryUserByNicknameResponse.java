package io.github.depromeet.knockknockbackend.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QueryUserByNicknameResponse {

    public final List<QueryUserByNicknameResponseElement> userList;

}
