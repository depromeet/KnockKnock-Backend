package io.github.depromeet.knockknockbackend.domain.relation.service;


import io.github.depromeet.knockknockbackend.domain.relation.domain.Relation;
import io.github.depromeet.knockknockbackend.domain.relation.domain.repository.RelationRepository;
import io.github.depromeet.knockknockbackend.domain.relation.exception.AlreadyFriendException;
import io.github.depromeet.knockknockbackend.domain.relation.exception.AlreadySendRequestException;
import io.github.depromeet.knockknockbackend.domain.relation.exception.FriendRequestNotFoundException;
import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.request.FriendRequest;
import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.response.QueryFriendListResponse;
import io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.response.QueryFriendListResponseElement;
import io.github.depromeet.knockknockbackend.domain.user.UserRelationService;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RelationService implements UserRelationService {

    private final RelationRepository relationRepository;
    private final UserUtils userUtils;

    public QueryFriendListResponse queryFriendList() {
        List<Relation> relationList =
                relationRepository.findFriendList(SecurityUtils.getCurrentUserId());

        List<QueryFriendListResponseElement> result =
                relationList.stream()
                        .map(
                                relation -> {
                                    if (relation.getReceiveUser()
                                            .getId()
                                            .equals(SecurityUtils.getCurrentUserId())) {
                                        return new QueryFriendListResponseElement(
                                                relation.getSendUserInfo());
                                    } else {
                                        return new QueryFriendListResponseElement(
                                                relation.getReceiveUserInfo());
                                    }
                                })
                        .collect(Collectors.toList());

        return new QueryFriendListResponse(result);
    }

    public HttpStatus sendFriendRequest(FriendRequest request) {
        if (relationRepository
                .findRelationBySendUserIdAndReceiveUserId(
                        SecurityUtils.getCurrentUserId(), request.getUserId())
                .isPresent()) {
            throw AlreadySendRequestException.EXCEPTION;
        }

        Relation relation =
                relationRepository
                        .findRelationBySendUserIdAndReceiveUserId(
                                request.getUserId(), SecurityUtils.getCurrentUserId())
                        .orElse(null);

        if (relation != null) {
            relation.updateFriend(true);
            relationRepository.save(relation);
            return HttpStatus.NO_CONTENT;
        }

        relationRepository.save(
                Relation.builder()
                        .sendUser(userUtils.getUserById(SecurityUtils.getCurrentUserId()))
                        .receiveUser(userUtils.getUserById(request.getUserId()))
                        .build());

        return HttpStatus.CREATED;
    }

    public void acceptRequest(FriendRequest request) {
        updateIsFriendWithValidate(request, true);
    }

    public void refuseRequest(FriendRequest request) {
        updateIsFriendWithValidate(request, false);
    }

    public boolean getIsFriend(Long userId) {
        return relationRepository.isFriend(SecurityUtils.getCurrentUserId(), userId);
    }

    private void updateIsFriendWithValidate(FriendRequest request, boolean isFriend) {
        if (!getIsFriend(request.getUserId())) {
            throw AlreadyFriendException.EXCEPTION;
        }

        relationRepository
                .findRelationBySendUserIdAndReceiveUserId(
                        SecurityUtils.getCurrentUserId(), request.getUserId())
                .orElseThrow(() -> FriendRequestNotFoundException.EXCEPTION);

        Relation relation =
                relationRepository
                        .findRelationBySendUserIdAndReceiveUserId(
                                SecurityUtils.getCurrentUserId(), request.getUserId())
                        .get();

        relation.updateFriend(isFriend);
        relationRepository.save(relation);
    }
}
