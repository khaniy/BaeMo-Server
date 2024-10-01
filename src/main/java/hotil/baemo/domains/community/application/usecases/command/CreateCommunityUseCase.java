package hotil.baemo.domains.community.application.usecases.command;

import hotil.baemo.domains.community.domain.aggregate.CommunityCreateAggregate;
import hotil.baemo.domains.community.domain.entity.CommunityId;

public interface CreateCommunityUseCase {
    CommunityId create(CommunityCreateAggregate aggregate);
}