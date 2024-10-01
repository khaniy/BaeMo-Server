package hotil.baemo.domains.clubs.domain.clubs.value;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsNonMember;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public interface Join {

    @Builder
    record Request(
            @NotNull
            ClubsNonMember clubsNonMember
    ) implements Join {
    }

    @Builder
    record Result(
            @NotNull
            ClubsNonMember clubsNonMember,
            @NotNull
            Boolean isAccept
    ) implements Join {
    }
}