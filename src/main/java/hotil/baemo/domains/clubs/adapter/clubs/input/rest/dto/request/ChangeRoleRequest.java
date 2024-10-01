package hotil.baemo.domains.clubs.adapter.clubs.input.rest.dto.request;

import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

public interface ChangeRoleRequest {
    @Builder
    record DTO(
        @NotNull
        @Positive
        Long targetId,
        @NotNull
        ClubsRole updateClubsRole
    ) implements ChangeRoleRequest {
    }
}