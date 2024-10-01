package hotil.baemo.domains.report.application.usecase;

import hotil.baemo.domains.report.domain.value.UserId;
import hotil.baemo.domains.report.domain.value.club.ClubId;
import hotil.baemo.domains.report.domain.value.club.ClubReportReason;
import hotil.baemo.domains.report.domain.value.post.PostId;
import hotil.baemo.domains.report.domain.value.post.PostReportReason;
import hotil.baemo.domains.report.domain.value.post.PostType;
import hotil.baemo.domains.report.domain.value.report.Description;
import hotil.baemo.domains.report.domain.value.user.UserReportReason;
import org.springframework.web.multipart.MultipartFile;

import java.util.EnumSet;
import java.util.List;

public interface SubmitReportUseCase {

    void submitUserReport(UserId userId, UserId targetId, EnumSet<UserReportReason> reasons, Description description);

    void submitClubReport(UserId userId, ClubId clubId, EnumSet<ClubReportReason> reasons, Description description);

    void submitPostReport(UserId userId, PostType domain, PostId postId, EnumSet<PostReportReason> reasons, Description description, List<MultipartFile> reportImages);
}
