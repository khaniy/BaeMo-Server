package hotil.baemo.domains.community.adapter.output.persistence.repository;

import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.repository.querydsl.QCommunityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityImageJpaRepository extends JpaRepository<CommunityImageEntity, Long>, QCommunityRepository {

    @Query("SELECT i " +
        "FROM CommunityImageEntity i " +
        "WHERE i.isDelete = false " +
        "AND i.communityId = :communityId")
    List<CommunityImageEntity> findAllByCommunityId(@Param("communityId") Long communityId);
}