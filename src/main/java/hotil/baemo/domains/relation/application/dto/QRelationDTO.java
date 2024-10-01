package hotil.baemo.domains.relation.application.dto;

import lombok.Builder;

public interface QRelationDTO {
    record FriendsListView(
        Long relationId,
        Long userId,
        String userName,
        String userProfileUrl,
        String userDescription
    ) implements QRelationDTO {
    }

    record BlockUserListView(
        Long relationId,
        Long userId,
        String userName,
        String userProfileUrl,
        String userDescription
    ) implements QRelationDTO {
    }

    @Builder
    record FindFriends(
        Long userId,
        String userName,
        String userProfileUrl,
        String userDescription
    ) implements QRelationDTO {
    }
}
