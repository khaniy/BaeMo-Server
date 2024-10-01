package hotil.baemo.domains.report.domain.aggregate;

import hotil.baemo.domains.report.domain.value.post.PostId;
import hotil.baemo.domains.report.domain.value.post.PostReportReason;
import hotil.baemo.domains.report.domain.value.post.PostType;
import hotil.baemo.domains.report.domain.value.report.Description;
import hotil.baemo.domains.report.domain.value.report.ReportId;
import hotil.baemo.domains.report.domain.value.UserId;
import hotil.baemo.domains.report.domain.value.user.UserReportReason;
import lombok.Builder;
import lombok.Getter;

import java.util.EnumSet;

@Getter
public class UserReport {
    private final ReportId id;

    private final UserId targetId;
    private final UserId reporterId;
    private final EnumSet<UserReportReason> reasons;
    private final Description description;

    @Builder
    private UserReport(ReportId id,  UserId targetId, UserId reporterId, EnumSet<UserReportReason> reasons, Description description) {
        this.id = id;
        this.targetId = targetId;
        this.reporterId = reporterId;
        this.reasons = reasons;
        this.description = description;
    }

    public static UserReport init(UserId targetId, UserId reporterId, EnumSet<UserReportReason> reasons, Description description){
        return UserReport.builder()
            .targetId(targetId)
            .reporterId(reporterId)
            .reasons(reasons)
            .description(description)
            .build();
    }

}