package hotil.baemo.domains.clubs.application.clubs.dto.query;

import lombok.Builder;

import java.util.List;

public interface PreviewResponse {
    @Builder
    record ClubsPreviewList(
        List<ClubsPreview> list
    ) implements PreviewResponse {
    }

    @Builder
    record ClubsPreview(
        Long clubsId,
        String name,
        String simpleDescription,
        String location,
        Long memberCount,
        String profileImagePath,
        String backgroundImagePath
    ) implements PreviewResponse {
    }
}