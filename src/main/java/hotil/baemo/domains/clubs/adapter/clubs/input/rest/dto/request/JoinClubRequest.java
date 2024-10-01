package hotil.baemo.domains.clubs.adapter.clubs.input.rest.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

public interface JoinClubRequest {

    @Builder
    record JoinHandleDTO(
        @NotNull(message = "모임 아이디가 비어있습니다.")
        @Positive(message = "모임 아이디가 잘못되었습니다.")
        Long clubsId,
        @NotNull(message = "유저 아이디가 비어있습니다.")
        @Positive(message = "유저 아이디가 잘못되었습니다.")
        Long nonMemberId,
        @NotNull(message = "승락/거절이 비어있습니다.")
        Boolean isAccept
    ) implements JoinClubRequest {
    }
}
