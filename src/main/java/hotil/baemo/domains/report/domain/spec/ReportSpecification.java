package hotil.baemo.domains.report.domain.spec;

import hotil.baemo.domains.report.domain.aggregate.ClubReport;
import hotil.baemo.domains.report.domain.aggregate.PostReport;
import hotil.baemo.domains.report.domain.aggregate.UserReport;
import hotil.baemo.domains.report.domain.value.UserId;
import hotil.baemo.domains.report.domain.value.club.ClubId;
import hotil.baemo.domains.report.domain.value.club.ClubReportReason;
import hotil.baemo.domains.report.domain.value.post.PostId;
import hotil.baemo.domains.report.domain.value.post.PostReportReason;
import hotil.baemo.domains.report.domain.value.post.PostType;
import hotil.baemo.domains.report.domain.value.report.Description;
import hotil.baemo.domains.report.domain.value.user.UserReportReason;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.EnumSet;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportSpecification {

    public static ReportSpecification spec() {
        return new ReportSpecification();
    }

    public UserReport createUserReport(
        UserId reporterId,
        UserId targetId,
        EnumSet<UserReportReason> reasons,
        Description description
    ) {
        return UserReport.init(targetId, reporterId, reasons, description);
    }

    public ClubReport createClubReport(
        UserId reporterId,
        ClubId clubId,
        EnumSet<ClubReportReason> reasons,
        Description description
    ) {
        return ClubReport.init(clubId, reporterId, reasons, description);
    }

    public PostReport createPostReport(
        UserId reporterId,
        PostType domain,
        PostId postId,
        EnumSet<PostReportReason> reasons,
        Description description
    ) {
        return PostReport.init(domain, postId, reporterId, reasons, description);
    }
}
