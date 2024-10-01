package hotil.baemo.domains.relation.application.usecases;

import hotil.baemo.domains.relation.application.dto.QRelationDTO;
import hotil.baemo.domains.relation.domain.value.UserCode;
import hotil.baemo.domains.relation.domain.value.UserId;

import java.util.List;

public interface RetrieveFriendsUseCase {
    List<QRelationDTO.FriendsListView> retrieveMyFriends(UserId userId);

    List<QRelationDTO.BlockUserListView> retrieveBlockUsers(UserId userId);

    List<QRelationDTO.FindFriends> retrieveFriend(UserId userId, UserCode userCode);
}
