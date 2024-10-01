package hotil.baemo.domains.community.adapter.output.persistence.repository;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityEntity;
import hotil.baemo.domains.community.adapter.output.persistence.jpa.CommunityImageQueryRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.querydsl.QCommunityRepository;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface CommunityJpaRepository extends JpaRepository<CommunityEntity, Long>, QCommunityRepository {

    List<CommunityEntity> findAllByWriter(Long writer);

    @Override
    @NonNull
    @Query(
        "SELECT c " +
            "FROM CommunityEntity c " +
            "WHERE c.isDelete = false " +
            "AND c.communityId = :communityId"
    )
    Optional<CommunityEntity> findById(@NonNull @Param("communityId") Long id);

    default CommunityEntity loadById(Long id) {
        return this.findById(id)
            .orElseThrow(() -> new CustomException(ResponseCode.COMMUNITY_NOT_FOUND));
    }

    default CommunityEntity loadById(CommunityId communityId) {
        return this.findById(communityId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.COMMUNITY_NOT_FOUND));
    }
}