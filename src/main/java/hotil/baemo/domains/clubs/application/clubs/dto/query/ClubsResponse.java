package hotil.baemo.domains.clubs.application.clubs.dto.query;

import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import lombok.Builder;

import java.util.List;

public interface ClubsResponse {
    @Builder
    record HomeDTO(
        String clubsName,
        String clubsSimpleDescription,
        String clubsDescription,
        String clubsLocation,
        String clubsProfileImagePath,
        String clubsBackgroundImagePath,
        Long clubsMemberCount,
        ClubsRole role
    ) implements ClubsResponse {
    }

    @Builder
    record MembersDTO(
        List<MemberDTO> list
    ) implements ClubsResponse {
    }

    @Builder
    record MemberDTO(
        Long id,
        String realName,
        String profileImage,
        ClubsRole role,
        String level
    ) implements ClubsResponse {
    }
}