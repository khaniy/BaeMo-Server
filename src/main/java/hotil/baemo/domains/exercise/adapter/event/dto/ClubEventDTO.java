package hotil.baemo.domains.exercise.adapter.event.dto;

import hotil.baemo.core.util.BaeMoObjectUtil;
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
        String clubsRole
    ) implements ClubEventDTO {
        public String asString() {
            return BaeMoObjectUtil.writeValueAsString(this);
        }
    }
}