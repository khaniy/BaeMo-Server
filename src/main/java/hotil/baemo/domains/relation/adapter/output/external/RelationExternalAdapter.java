package hotil.baemo.domains.relation.adapter.output.external;

import hotil.baemo.domains.relation.adapter.output.external.query.RelationExternalQuery;
import hotil.baemo.domains.relation.application.dto.QRelationDTO;
import hotil.baemo.domains.relation.application.ports.output.RelationExternalOutPort;
import hotil.baemo.domains.relation.domain.value.UserCode;
import hotil.baemo.domains.relation.domain.value.UserId;
import hotil.baemo.domains.relation.domain.value.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelationExternalAdapter implements RelationExternalOutPort {

    private final RelationExternalQuery relationExternalQuery;

    @Override
    public UserId getUserByUserCode(UserCode userCode, UserName userName) {
        final var userId = relationExternalQuery.getUserIdByUserCodeAndName(userName.name(), userCode.code());
        return new UserId(userId);
    }

    @Override
    public List<QRelationDTO.FindFriends> findFriend(UserId userId, UserCode userCode) {
        return relationExternalQuery.getUserByUserCode(userId.id(), userCode.code());
    }
}
