package io.github.depromeet.knockknockbackend.domain.relation.presentation;

import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.response.QueryFriendListResponse;
import io.github.depromeet.knockknockbackend.domain.relation.service.RelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/relations")
@RestController
public class RelationController {

    private final RelationService relationService;

    @GetMapping
    public QueryFriendListResponse queryFriendList() {
        return relationService.queryFriendList();
    }

}
