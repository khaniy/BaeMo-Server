package hotil.baemo.core.event;

import lombok.Builder;

public interface UserTopic {
    @Builder
    record DeletedEvent(
        Long userId
    ) implements UserTopic {
    }
}
