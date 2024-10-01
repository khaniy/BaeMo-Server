package hotil.baemo.domains.relation.adapter.output.persistence;

import hotil.baemo.domains.relation.adapter.output.persistence.repository.QueryRelationQRepository;
import hotil.baemo.domains.relation.application.dto.QRelationDTO;
import hotil.baemo.domains.relation.application.ports.output.QueryRelationOutputPort;
import hotil.baemo.domains.relation.domain.value.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryRelationPersistenceAdapter implements QueryRelationOutputPort {

    private final QueryRelationQRepository queryRelationQRepository;

    @Override
    public List<QRelationDTO.FriendsListView> getFriends(UserId userId) {
        return queryRelationQRepository.findAllFriends(userId.id());
    }

    @Override
    public List<QRelationDTO.BlockUserListView> getBlockUsers(UserId userId) {
        return queryRelationQRepository.findAllBlockUsers(userId.id());
    }
}
