package hotil.baemo.domains.notification.adapter.event.dto;

import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import lombok.Builder;

public interface ClubEventDTO {
    @Builder
    record Create(
        Long clubsId,
        Long userId
    ) implements ClubEventDTO {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record Join(
        Long clubsId,
        Long userId
    ) implements ClubEventDTO {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record JoinRequested(
        Long clubsId,
        Long userId
    ) implements ClubEventDTO {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }


    @Builder
    record KickUser(
        Long clubsId,
        Long userId
    ) implements ClubEventDTO {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record Delete(
        Long clubsId
    ) implements ClubEventDTO {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }

    @Builder
    record UpdateRole(
        Long clubsId,
        Long userId,
        ClubsRole clubsRole
    ) implements ClubEventDTO {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }
}