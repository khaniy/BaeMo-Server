package hotil.baemo.domains.clubs.adapter.clubs.input.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

public interface CommandClubsRequest {
    @Builder
    record CreateClubsDTO(
        @NotBlank(message = "모임 이름은 필수입니다.")
        @Length(max = 20, message = "제목은 20자 이하로 작성 가능합니다.")
        String clubsName,
        @NotBlank(message = "모임의 간략한 설명은 필수입니다.")
        String clubsSimpleDescription,
        @NotBlank(message = "모임 지역은 필수입니다.")
        String clubsLocation,
        @NotBlank(message = "모임 설명은 필수입니다.")
        @Length(max = 500, message = "500자 이내로 작성 가능합니다.")
        String clubsDescription
    ) implements CommandClubsRequest {
    }

    @Builder
    record UpdateClubsDTO(
        @NotNull(message = "모임 아이디가 비어있습니다.")
        @Positive(message = "모임 아이디가 잘못되었습니다.")
        Long clubsId,
        @NotBlank(message = "모임 이름은 필수입니다.")
        @Length(max = 20, message = "제목은 20자 이하로 작성 가능합니다.")
        String clubsName,
        @NotBlank(message = "모임의 간략한 설명은 필수입니다.")
        @Length(max = 500, message = "500자 이내로 작성 가능합니다.")
        String clubsSimpleDescription,
        @NotBlank(message = "모임 지역은 필수입니다.")
        String clubsLocation,
        @NotBlank(message = "모임 설명은 필수입니다.")
        String clubsDescription
    ) implements CommandClubsRequest {
    }
}