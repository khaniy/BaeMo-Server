package hotil.baemo.domains.relation.application.ports.output;

import hotil.baemo.domains.relation.application.dto.QRelationDTO;
import hotil.baemo.domains.relation.domain.aggregate.Relation;
import hotil.baemo.domains.relation.domain.value.UserId;

import java.util.List;

public interface QueryRelationOutputPort {
    List<QRelationDTO.FriendsListView> getFriends(UserId userId);

    List<QRelationDTO.BlockUserListView> getBlockUsers(UserId userId);
}
