package io.github.depromeet.knockknockbackend.global.utils.relation;


import io.github.depromeet.knockknockbackend.domain.relation.domain.Relation;
import io.github.depromeet.knockknockbackend.domain.relation.domain.repository.RelationRepository;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelationUtilsImpl implements RelationUtils{

    private final RelationRepository relationRepository;

    public List<Long> findMyFriendUserIdList(Long myId){
        List<Relation> relationList = relationRepository.findFriendList(SecurityUtils.getCurrentUserId());

        List<Long> result = relationList.stream().map(
            relation -> {
                if(relation.getReceiveUser().getId().equals(myId)) {
                    return relation.getSendUser().getId();
                } else {
                    return relation.getReceiveUser().getId();
                }
            }
        ).filter(userId -> userId.equals(myId)).collect(Collectors.toList());
        return result;
    }
}
