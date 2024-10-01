package hotil.baemo.domains.community.adapter.output.persistence.jpa.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.QCommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.jpa.CommunityImageQueryRepository;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
class CommunityImageQueryDsl implements CommunityImageQueryRepository {
    private static final QCommunityImageEntity IMAGE = QCommunityImageEntity.communityImageEntity;
    private final JPAQueryFactory factory;

    @Override
    public void delete(CommunityId communityId) {
        factory.selectFrom(IMAGE)
            .where(
                IMAGE.communityId.eq(communityId.id())
                    .and(IMAGE.isDelete.isFalse())
            )
            .fetch()
            .forEach(CommunityImageEntity::delete);
    }
}