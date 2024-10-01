package hotil.baemo.domains.community.application.usecases.command;

import hotil.baemo.domains.community.domain.aggregate.CommunityUpdateAggregate;

public interface UpdateCommunityUseCase {
    void update(CommunityUpdateAggregate communityUpdateAggregate);
}
