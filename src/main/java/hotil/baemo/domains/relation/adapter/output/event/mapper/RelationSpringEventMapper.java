package hotil.baemo.domains.relation.adapter.output.event.mapper;


import hotil.baemo.core.event.RelationTopic;
import hotil.baemo.domains.relation.domain.value.UserId;

public class RelationSpringEventMapper {
	public static RelationTopic.sendFriendRequestEvent friendRequestEvent(UserId userId,UserId targetId) {
		var builder = RelationTopic.sendFriendRequestEvent.builder()
			.userId(userId.id())
			.targetId(targetId.id());
		return builder.build();
	}

	public static RelationTopic.friendRequestApprovedEvent friendRequestApprovedEvent(UserId userId,UserId targetId) {
		var builder = RelationTopic.friendRequestApprovedEvent.builder()
			.userId(userId.id())
			.targetId(targetId.id());
		return builder.build();
	}

	public static RelationTopic.friendDeletedEvent friendDeletedEvent(UserId userId,UserId targetId) {
		var builder = RelationTopic.friendDeletedEvent.builder()
			.userId(userId.id())
			.targetId(targetId.id());
		return builder.build();
	}

}
