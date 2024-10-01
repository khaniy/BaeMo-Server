package hotil.baemo.domains.relation.application.ports.output;

import hotil.baemo.domains.relation.application.dto.QRelationDTO;
import hotil.baemo.domains.relation.domain.value.UserCode;
import hotil.baemo.domains.relation.domain.value.UserId;
import hotil.baemo.domains.relation.domain.value.UserName;

import java.util.List;

public interface RelationExternalOutPort {
    UserId getUserByUserCode(UserCode userCode, UserName userName);

    List<QRelationDTO.FindFriends> findFriend(UserId userId, UserCode userCode);
}
