package hotil.baemo.core.event;


public interface ChatTopic {

    record ChatSentEvent(
        String content,
        Long userId,
        String roomId
    ) implements ChatTopic {
    }

    record ChatCreatedEvent(
        Long userId,
        Long targetId,
        String chatRole
    ) implements ChatTopic {
    }

    record ChatDeletedEvent(
        Long userId,
        String chatRoomId
    ) implements ChatTopic {
    }

    record ChatRoomCreated(
        String chatRoomId,
        boolean isNewChatRoom
    ) implements ChatTopic {
    }
}

