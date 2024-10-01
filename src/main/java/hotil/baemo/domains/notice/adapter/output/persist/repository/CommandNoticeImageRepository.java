package hotil.baemo.domains.notice.adapter.output.persist.repository;

import hotil.baemo.domains.notice.adapter.output.persist.entity.NoticeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommandNoticeImageRepository extends JpaRepository<NoticeImageEntity, Long> {

    @Modifying
    @Query("DELETE FROM NoticeImageEntity e WHERE e.noticeId=:noticeId")
    void deleteAllByNoticeId(@Param("noticeId") Long noticeId);
}
