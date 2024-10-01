package hotil.baemo.domains.report.adapter.input.rest.dto.request;

import hotil.baemo.domains.report.domain.value.club.ClubReportReason;
import hotil.baemo.domains.report.domain.value.post.PostReportReason;
import hotil.baemo.domains.report.domain.value.post.PostType;
import hotil.baemo.domains.report.domain.value.user.UserReportReason;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.EnumSet;

public interface ReportRequest {
    record UserReportDTO(
        @NotNull
        @Positive
        Long targetUserId,
        @Size(min = 1, max = 100)
        EnumSet<UserReportReason> reasons,
        @NotNull
        String description
    ) implements ReportRequest {
    }
    record ClubReportDTO(
        @NotNull
        @Positive
        Long clubId,
        @Size(min = 1, max = 100)
        EnumSet<ClubReportReason> reasons,
        @NotNull
        String description
    ) implements ReportRequest {
    }
    record PostReportDTO(
        @NotNull
        PostType type,
        @NotNull
        @Positive
        Long postId,
        @Size(min = 1, max = 100)
        EnumSet<PostReportReason> reasons,
        @NotNull
        String description
    ) implements ReportRequest {
    }
}