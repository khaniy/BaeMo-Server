package hotil.baemo.core.event;

import hotil.baemo.core.util.BaeMoObjectUtil;
import lombok.Builder;

public interface ClubTopic {
    @Builder
    record CreatedEvent(
        Long clubsId,
        Long userId
    ) implements ClubTopic {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record PostCreated(
        Long clubsId,
        Long clubPostId,
        Long userId,
        String title,
        String content
    ) implements ClubTopic {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record PostCommented(
        Long clubsId,
        Long clubPostId,
        Long userId,
        String title,
        String content
    ) implements ClubTopic {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }


    @Builder
    record JoinedEvent(
        Long clubsId,
        Long userId
    ) implements ClubTopic {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record JoinRequestedEvent(
        Long clubsId,
        Long userId
    ) implements ClubTopic {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }


    @Builder
    record UserExpelledEvent(
        Long clubsId,
        Long userId
    ) implements ClubTopic {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record UserLeftEvent(
        Long clubsId,
        Long userId
    ) implements ClubTopic {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record DeletedEvent(
        Long clubsId
    ) implements ClubTopic {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }


    @Builder
    record MemberRoleUpdatedEvent(
        Long clubsId,
        Long userId,
        String clubsRole
    ) implements ClubTopic {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }
}