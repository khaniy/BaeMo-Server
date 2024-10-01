package hotil.baemo.domains.notice.adapter.output.persist;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.notice.adapter.output.persist.entity.NoticeEntity;
import hotil.baemo.domains.notice.adapter.output.persist.entity.NoticeImageEntity;
import hotil.baemo.domains.notice.adapter.output.persist.mapper.NoticeEntityMapper;
import hotil.baemo.domains.notice.adapter.output.persist.mapper.NoticeImageEntityMapper;
import hotil.baemo.domains.notice.adapter.output.persist.repository.CommandNoticeImageRepository;
import hotil.baemo.domains.notice.adapter.output.persist.repository.CommandNoticeRepository;
import hotil.baemo.domains.notice.application.port.output.CommandNoticeOutPort;
import hotil.baemo.domains.notice.domain.aggregate.Notice;
import hotil.baemo.domains.notice.domain.value.notice.NoticeId;
import hotil.baemo.domains.notice.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static hotil.baemo.config.cache.CacheProperties.CacheValue.NOTICE_CACHE;

@Service
@RequiredArgsConstructor
public class CommandNoticePersistAdapter implements CommandNoticeOutPort {

    private final CommandNoticeRepository commandNoticeRepository;
    private final CommandNoticeImageRepository commandNoticeImageRepository;

    @Override
    public void save(Notice notice) {
        NoticeEntity entity = NoticeEntityMapper.toEntity(notice);
        commandNoticeRepository.save(entity);
        commandNoticeImageRepository.deleteAllByNoticeId(entity.getId());
        List<NoticeImageEntity> entities = NoticeImageEntityMapper.toEntities(entity.getId(), notice);
        commandNoticeImageRepository.saveAll(entities);
    }

    @Override
    public Notice getNotice(NoticeId noticeId) {
        NoticeEntity entity = commandNoticeRepository.findById(noticeId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.ETC_ERROR));
        return NoticeEntityMapper.toNotice(entity);
    }

    @Override
    @Cacheable(value = NOTICE_CACHE, key = "'notice_id_' + #noticeId + '_' + #userId")
    public boolean updateNoticeViewCount(UserId userId, NoticeId noticeId) {
        NoticeEntity entity = commandNoticeRepository.findById(noticeId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.ETC_ERROR));
        entity.incrementViewCount();
        return true;
    }

    @Override
    public void delete(NoticeId noticeId) {
        commandNoticeRepository.deleteById(noticeId.id());
        commandNoticeImageRepository.deleteAllByNoticeId(noticeId.id());
    }
}
