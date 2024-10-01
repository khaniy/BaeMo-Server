package hotil.baemo.domains.report.domain.aggregate;

import hotil.baemo.domains.report.domain.value.post.PostId;
import hotil.baemo.domains.report.domain.value.post.PostType;
import hotil.baemo.domains.report.domain.value.report.Description;
import hotil.baemo.domains.report.domain.value.UserId;
import hotil.baemo.domains.report.domain.value.post.PostReportReason;
import hotil.baemo.domains.report.domain.value.report.ReportId;
import lombok.Builder;
import lombok.Getter;

import java.util.EnumSet;

@Getter
public class PostReport {
    private final ReportId id;

    private final PostType type;
    private final PostId postId;
    private final UserId reporterId;
    private final EnumSet<PostReportReason> reasons;
    private final Description description;

    @Builder
    private PostReport(ReportId id, PostType type, PostId postId, UserId reporterId, EnumSet<PostReportReason> reasons, Description description) {
        this.id = id;
        this.type = type;
        this.postId = postId;
        this.reporterId = reporterId;
        this.reasons = reasons;
        this.description = description;
    }

    public static PostReport init(PostType type, PostId postId, UserId reporterId, EnumSet<PostReportReason> reasons, Description description){
        return PostReport.builder()
            .type(type)
            .postId(postId)
            .reporterId(reporterId)
            .reasons(reasons)
            .description(description)
            .build();
    }

}