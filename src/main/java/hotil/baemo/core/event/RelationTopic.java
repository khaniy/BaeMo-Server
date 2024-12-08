package hotil.baemo.core.event;

import lombok.Builder;

public interface RelationTopic {
	@Builder
	record sendFriendRequestEvent(
		Long userId,
		Long targetId
	) implements RelationTopic {
	}
	@Builder
	record  friendRequestApprovedEvent(
		Long userId,
		Long targetId
	) implements RelationTopic {
	}

	@Builder
	record  friendDeletedEvent(
		Long userId,
		Long targetId
	) implements RelationTopic {
	}
}
