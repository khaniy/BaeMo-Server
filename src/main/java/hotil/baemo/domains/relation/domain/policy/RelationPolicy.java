package hotil.baemo.domains.relation.domain.policy;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.event.RelationEvent;
import hotil.baemo.domains.relation.domain.service.RelationService;
import hotil.baemo.domains.relation.domain.value.RelationType;
import hotil.baemo.domains.relation.domain.value.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RelationPolicy {
    private final RelationService relationService;
    private final RelationEvent relationEvent;

    public void checkFriendPolicy(UserId userId, UserId targetId) {
        Optional<Relation> existedRelation = relationService.getRelation(userId, targetId);
        if (existedRelation.isPresent()) {
            var relation = existedRelation.get();
            switch (relation.getType()) {
                case FRIEND:
                    throw new CustomException(ResponseCode.ALREADY_FRIEND);
                case BLOCK:
                    throw new CustomException(ResponseCode.BLOCKED_USER);
            }
            relationService.delete(existedRelation.get());
        }
    }

    public void checkBlockPolicy(UserId userId, UserId targetId) {
        Optional<Relation> existedRelation = relationService.getRelation(userId, targetId);
        if (existedRelation.isPresent()) {
            var relation = existedRelation.get();
            switch (relation.getType()) {
                case BLOCK:
                    throw new CustomException(ResponseCode.ALREADY_BLOCKED);
                case FRIEND:
                    relationEvent.friendDeleted(userId, targetId);
            }
            relationService.delete(relation);
        }
    }
}
