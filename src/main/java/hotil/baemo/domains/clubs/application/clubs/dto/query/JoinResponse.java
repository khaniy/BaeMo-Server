package hotil.baemo.domains.clubs.application.clubs.dto.query;

import lombok.Builder;

import java.util.List;

public interface JoinResponse {
    @Builder
    record GetDTOList(
        List<GetDTO> list
    ) {
    }

    @Builder
    record GetDTO(
        Long userId,
        String realName,
        String profilePath
    ) {
    }
}
