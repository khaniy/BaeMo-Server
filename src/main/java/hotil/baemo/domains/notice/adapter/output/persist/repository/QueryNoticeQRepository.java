package hotil.baemo.domains.notice.adapter.output.persist.repository;

import com.querydsl.core.types.FactoryExpressionBase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.notice.adapter.output.persist.entity.QNoticeEntity;
import hotil.baemo.domains.notice.adapter.output.persist.entity.QNoticeImageEntity;
import hotil.baemo.domains.notice.application.dto.QNoticeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryNoticeQRepository {
    private final JPAQueryFactory queryFactory;
    private static final QNoticeEntity NOTICE = QNoticeEntity.noticeEntity;
    private static final QNoticeImageEntity NOTICE_IMAGE = QNoticeImageEntity.noticeImageEntity;
    private static final QNoticeDTO.WriterInfo ADMIN_INFO = QNoticeDTO.WriterInfo.builder()
        .writerName("배모 운영진")
        .writerProfileUrl("https://rlf8fs4ej.toastcdn.net/dev/USERS/USERS-9c874b66-9615-48c1-a80e-0f8538f3357e")
        .build();

    public List<QNoticeDTO.NoticeListView> findAllNotice() {
        List<QNoticeDTO.NoticeInfo> noticeInfos = queryFactory.select(constructNotice())
            .from(NOTICE)
            .leftJoin(NOTICE_IMAGE).on(NOTICE_IMAGE.noticeId.eq(NOTICE.id)
                .and(NOTICE_IMAGE.isThumbnail.eq(true))
                .and(NOTICE_IMAGE.isDel.eq(false)))
            .where(NOTICE.isDel.eq(false))
            .orderBy(NOTICE.updatedAt.desc())
            .fetch();
        return noticeInfos.stream().map(n -> QNoticeDTO.NoticeListView.builder()
                .noticeInfo(n)
                .writerInfo(ADMIN_INFO)
                .build()
            )
            .collect(Collectors.toList());
    }

    public List<QNoticeDTO.NoticeListView> findLatestNotice() {
        List<QNoticeDTO.NoticeInfo> noticeInfos = queryFactory.select(constructNotice())
            .from(NOTICE)
            .leftJoin(NOTICE_IMAGE).on(NOTICE_IMAGE.noticeId.eq(NOTICE.id)
                .and(NOTICE_IMAGE.isThumbnail.eq(true))
                .and(NOTICE_IMAGE.isDel.eq(false)))
            .where(NOTICE.isDel.eq(false))
            .orderBy(NOTICE.updatedAt.desc())
            .limit(1)
            .fetch();
        return noticeInfos.stream().map(n -> QNoticeDTO.NoticeListView.builder()
                .noticeInfo(n)
                .writerInfo(ADMIN_INFO)
                .build()
            )
            .collect(Collectors.toList());
    }

    public QNoticeDTO.NoticeDetailView findNoticeDetail(Long noticeId) {
        QNoticeDTO.NoticeInfo noticeInfo = queryFactory.select(constructNotice())
            .from(NOTICE)
            .leftJoin(NOTICE_IMAGE).on(NOTICE_IMAGE.noticeId.eq(NOTICE.id)
                .and(NOTICE_IMAGE.isThumbnail.eq(true))
                .and(NOTICE_IMAGE.isDel.eq(false)))
            .where(NOTICE.isDel.eq(false)
                .and(NOTICE.id.eq(noticeId))
            )
            .fetchOne();
        List<QNoticeDTO.ImageInfo> images = queryFactory.select(constructImage())
            .from(NOTICE_IMAGE)
            .where(NOTICE_IMAGE.noticeId.eq(noticeId)
                .and(NOTICE_IMAGE.isDel.eq(false))
            )
            .fetch();
        return QNoticeDTO.NoticeDetailView.builder()
            .noticeInfo(noticeInfo)
            .images(images)
            .writerInfo(ADMIN_INFO)
            .build();
    }

    private FactoryExpressionBase<QNoticeDTO.NoticeInfo> constructNotice() {
        return Projections.constructor(QNoticeDTO.NoticeInfo.class,
            NOTICE.id,
            NOTICE.title,
            NOTICE.content,
            NOTICE_IMAGE.imagePath,
            NOTICE.updatedAt,
            NOTICE.viewCount
        );
    }

    private FactoryExpressionBase<QNoticeDTO.ImageInfo> constructImage() {
        return Projections.constructor(QNoticeDTO.ImageInfo.class,
            NOTICE_IMAGE.imagePath,
            NOTICE_IMAGE.orderNumber,
            NOTICE_IMAGE.isThumbnail
        );
    }


}
