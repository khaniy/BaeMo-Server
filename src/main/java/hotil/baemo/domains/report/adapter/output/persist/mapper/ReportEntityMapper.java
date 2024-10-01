//package hotil.baemo.domains.report.adapter.output.persist.mapper;
//
//import hotil.baemo.domains.report.adapter.output.persist.entity.ReportEntity;
//import hotil.baemo.domains.report.domain.aggregate.PostReport;
//import hotil.baemo.domains.report.domain.value.report.Description;
//import hotil.baemo.domains.report.domain.value.post.PostId;
//import hotil.baemo.domains.report.domain.value.report.ReportId;
//import hotil.baemo.domains.report.domain.value.UserId;
//
//public class ReportEntityMapper {
//
//    public static ReportEntity toEntity(PostReport postReport) {
//        return ReportEntity.builder()
//            .id(postReport.getReporterId() != null ? postReport.getReporterId().id() : null )
//            .domain(postReport.getType())
//            .postId(postReport.getPostId().id())
//            .reporterId(postReport.getReporterId().id())
//            .reasons(postReport.getReasons())
//            .description(postReport.getDescription().description())
//            .build();
//    }
//
//    public static PostReport toReport(ReportEntity entity) {
//        return PostReport.builder()
//            .id(new ReportId(entity.getId()))
//            .domain(entity.getDomain())
//            .postId(new PostId(entity.getPostId()))
//            .reporterId(new UserId(entity.getReporterId()))
//            .reasons(entity.getReasons())
//            .description(new Description(entity.getDescription()))
//            .build();
//    }
//}
