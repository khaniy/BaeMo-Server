package hotil.baemo.domains.notice.adapter.output.persist.repository;

import hotil.baemo.domains.notice.adapter.output.persist.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandNoticeRepository extends JpaRepository<NoticeEntity, Long> {
}
