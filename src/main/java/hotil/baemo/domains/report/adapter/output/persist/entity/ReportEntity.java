//package hotil.baemo.domains.report.adapter.output.persist.entity;
//
//
//import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
//import hotil.baemo.domains.report.adapter.output.persist.entity.converter.RelationReasonConverter;
//import hotil.baemo.domains.report.domain.value.post.PostType;
//import hotil.baemo.domains.report.domain.value.post.PostReportReason;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.SQLDelete;
//
//import java.util.EnumSet;
//
//@Getter
//@Entity
//@Table(name = "tb_report")
//@SQLDelete(sql = "UPDATE tb_report SET is_del = true WHERE id = ?")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class ReportEntity extends BaeMoBaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    private PostType domain;
//    private Long postId;
//    private Long reporterId;
//    @Convert(converter = RelationReasonConverter.class)
//    private EnumSet<PostReportReason> reasons;
//    private String description;
//    @Column(name = "is_del")
//    private Boolean isDel;
//
//    @Builder
//    private ReportEntity(Long id, PostType domain, Long postId, Long reporterId, EnumSet<PostReportReason> reasons, String description, Boolean isDel) {
//        this.id = id;
//        this.domain = domain;
//        this.postId = postId;
//        this.reporterId = reporterId;
//        this.reasons = reasons;
//        this.description = description;
//        this.isDel = isDel != null ? isDel : false; // Set default to false if null
//    }
//}