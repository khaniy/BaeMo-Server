package hotil.baemo.domains.notice.application.dto;

import hotil.baemo.domains.notice.domain.value.user.NoticeUserRole;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

public interface QNoticeDTO {

    @Builder
    record RetrieveAllNotice(
        List<NoticeListView> notices,
        NoticeUserRole role
    ) implements QNoticeDTO{
    }

    @Builder
    record RetrieveNoticeDetail(
        NoticeDetailView noticeDetail,
        NoticeUserRole role
    ) implements QNoticeDTO{
    }

    @Builder
    record NoticeListView(
        WriterInfo writerInfo,
        NoticeInfo noticeInfo
    ) implements QNoticeDTO {
    }

    @Builder
    record NoticeDetailView(
        WriterInfo writerInfo,
        NoticeInfo noticeInfo,
        List<ImageInfo> images
    ) implements QNoticeDTO {
    }

    @Builder
    record NoticeInfo(
        Long noticeId,
        String title,
        String content,
        String thumbnailPath,
        Instant updatedAt,

        Long viewCount
    ) implements QNoticeDTO {
    }

    @Builder
    record WriterInfo(
        String writerName,
        String writerProfileUrl
    ) implements QNoticeDTO {
    }

    @Builder
    record ImageInfo(
        String path,
        Long orderNumber,
        Boolean isThumbnail
    ) {
    }
}
