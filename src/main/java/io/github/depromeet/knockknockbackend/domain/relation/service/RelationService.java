package io.github.depromeet.knockknockbackend.domain.relation.service;

import io.github.depromeet.knockknockbackend.domain.relation.domain.Relation;
import io.github.depromeet.knockknockbackend.domain.relation.domain.repository.RelationRepository;
import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.response.QueryFriendListResponse;
import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.response.QueryFriendListResponseElement;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RelationService {

    private final RelationRepository relationRepository;

    public QueryFriendListResponse queryFriendList() {
        List<Relation> relationList = relationRepository.findFriendList(SecurityUtils.getCurrentUserId());

        List<QueryFriendListResponseElement> result = relationList.stream().map(
                relation -> {
                    if(relation.getReceiveUser().getId().equals(SecurityUtils.getCurrentUserId())) {
                        return new QueryFriendListResponseElement(relation.getSendUserInfo());
                    } else {
                        return new QueryFriendListResponseElement(relation.getReceiveUserInfo());
                    }
                }
        ).collect(Collectors.toList());

        return new QueryFriendListResponse(result);
    }

}
