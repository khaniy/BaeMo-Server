package hotil.baemo.domains.clubs.application.dto;

import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import lombok.Builder;

import java.util.List;

public interface QClubDTO {

    @Builder
    record ClubDetailView(
        String clubsName,
        String clubsSimpleDescription,
        String clubsDescription,
        String clubsLocation,
        String clubsProfileImagePath,
        String clubsBackgroundImagePath,
        Long clubsMemberCount,
        ClubRole role
    ) implements QClubDTO {
    }

    @Builder
    record ClubPreviewList(
        List<ClubPreview> list
    ) implements QClubDTO {
    }

    @Builder
    record ClubPreview(
        Long clubsId,
        String name,
        String simpleDescription,
        String location,
        Long memberCount,
        String profileImagePath,
        String backgroundImagePath
    ) implements QClubDTO {
    }
}