package hotil.baemo.domains.report.domain.aggregate;

import hotil.baemo.domains.report.domain.value.UserId;
import hotil.baemo.domains.report.domain.value.club.ClubId;
import hotil.baemo.domains.report.domain.value.club.ClubReportReason;
import hotil.baemo.domains.report.domain.value.post.PostId;
import hotil.baemo.domains.report.domain.value.report.Description;
import hotil.baemo.domains.report.domain.value.report.ReportId;
import lombok.Builder;
import lombok.Getter;

import java.util.EnumSet;

@Getter
public class ClubReport {
    private final ReportId id;

    private final ClubId clubId;
    private final UserId reporterId;
    private final EnumSet<ClubReportReason> reasons;
    private final Description description;

    @Builder
    private ClubReport(ReportId id, ClubId clubId, UserId reporterId, EnumSet<ClubReportReason> reasons, Description description) {
        this.id = id;
        this.clubId = clubId;
        this.reporterId = reporterId;
        this.reasons = reasons;
        this.description = description;
    }

    public static ClubReport init(ClubId clubId, UserId reporterId, EnumSet<ClubReportReason> reasons, Description description) {
        return ClubReport.builder()
            .clubId(clubId)
            .reporterId(reporterId)
            .reasons(reasons)
            .description(description)
            .build();
    }

}