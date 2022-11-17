package io.github.depromeet.knockknockbackend.domain.relation.service;

import io.github.depromeet.knockknockbackend.domain.relation.domain.Relation;
import io.github.depromeet.knockknockbackend.domain.relation.domain.repository.RelationRepository;
import io.github.depromeet.knockknockbackend.domain.relation.exception.AlreadySendRequestException;
import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.request.SendFriendRequest;
import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.response.QueryFriendListResponse;
import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.response.QueryFriendListResponseElement;
import io.github.depromeet.knockknockbackend.domain.user.UserUtils;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RelationService {

    private final RelationRepository relationRepository;
    private final UserUtils userUtils;

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

    public HttpStatus sendFriendRequest(SendFriendRequest request) {
        if(relationRepository.isAlreadyRequested(SecurityUtils.getCurrentUserId(), request.getUserId()).isPresent()) {
            throw AlreadySendRequestException.EXCEPTION;
        }

        Relation relation = relationRepository.isPendingRequest(SecurityUtils.getCurrentUserId(), request.getUserId())
                .orElse(null);

        if(relation != null) {
            relation.updateFriend(true);
            relationRepository.save(relation);
            return HttpStatus.NO_CONTENT;
        }

        relationRepository.save(
                Relation.builder()
                        .sendUser(userUtils.getUserById(SecurityUtils.getCurrentUserId()))
                        .receiveUser(userUtils.getUserById(request.getUserId()))
                        .build()
        );

        return HttpStatus.CREATED;

    }

}
