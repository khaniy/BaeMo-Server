package hotil.baemo.domains.clubs.adapter.post.output.event.message;

import hotil.baemo.core.util.BaeMoObjectUtil;
import lombok.Builder;

public interface ClubsMessage {
    @Builder
    record Create(
        Long clubsId,
        Long userId
    ) implements ClubsMessage {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record Join(
        Long clubsId,
        Long userId
    ) implements ClubsMessage {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record JoinRequested(
        Long clubsId,
        Long userId
    ) implements ClubsMessage {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }


    @Builder
    record KickUser(
        Long clubsId,
        Long userId
    ) implements ClubsMessage {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record Delete(
        Long clubsId
    ) implements ClubsMessage {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record UpdateRole(
        Long clubsId,
        Long userId,
        String clubsRole
    ) implements ClubsMessage {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }
}