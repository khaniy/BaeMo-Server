package hotil.baemo.domains.relation.application.ports.input;

import hotil.baemo.domains.relation.application.dto.QRelationDTO;
import hotil.baemo.domains.relation.application.ports.output.QueryRelationOutputPort;
import hotil.baemo.domains.relation.application.ports.output.RelationExternalOutPort;
import hotil.baemo.domains.relation.application.usecases.RetrieveFriendsUseCase;
import hotil.baemo.domains.relation.domain.value.UserCode;
import hotil.baemo.domains.relation.domain.value.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveRelationInputPort implements RetrieveFriendsUseCase {
    private final QueryRelationOutputPort queryRelationOutputPort;
    private final RelationExternalOutPort relationExternalOutPort;

    @Override
    public List<QRelationDTO.FriendsListView> retrieveMyFriends(UserId userId) {
        return queryRelationOutputPort.getFriends(userId);
    }

    @Override
    public List<QRelationDTO.BlockUserListView> retrieveBlockUsers(UserId userId) {
        return queryRelationOutputPort.getBlockUsers(userId);
    }

    @Override
    public List<QRelationDTO.FindFriends> retrieveFriend(UserId userId, UserCode userCode) {
        return relationExternalOutPort.findFriend(userId, userCode);

    }
}