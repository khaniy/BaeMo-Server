package hotil.baemo.domains.report.application.port.input;

import hotil.baemo.domains.report.application.port.output.CommandReportOutPort;
import hotil.baemo.domains.report.application.usecase.SubmitReportUseCase;
import hotil.baemo.domains.report.domain.aggregate.PostReport;
import hotil.baemo.domains.report.domain.spec.ReportSpecification;
import hotil.baemo.domains.report.domain.value.UserId;
import hotil.baemo.domains.report.domain.value.club.ClubId;
import hotil.baemo.domains.report.domain.value.club.ClubReportReason;
import hotil.baemo.domains.report.domain.value.post.PostId;
import hotil.baemo.domains.report.domain.value.post.PostReportReason;
import hotil.baemo.domains.report.domain.value.post.PostType;
import hotil.baemo.domains.report.domain.value.report.Description;
import hotil.baemo.domains.report.domain.value.user.UserReportReason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.EnumSet;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SubmitReportInPort implements SubmitReportUseCase {

    private final CommandReportOutPort commandReportOutPort;


    @Override
    public void submitUserReport(UserId userId, UserId targetId, EnumSet<UserReportReason> reasons, Description description) {
        ReportSpecification.spec().createUserReport(userId, targetId, reasons, description);
    }

    @Override
    public void submitClubReport(UserId userId, ClubId clubId, EnumSet<ClubReportReason> reasons, Description description) {
        ReportSpecification.spec().createClubReport(userId, clubId, reasons, description);
    }

    @Override
    public void submitPostReport(UserId userId, PostType domain, PostId postId, EnumSet<PostReportReason> reasons, Description description, List<MultipartFile> reportImages) {
        PostReport postReport = ReportSpecification.spec().createPostReport(userId, domain, postId, reasons, description);
        commandReportOutPort.save(postReport);
    }
}
