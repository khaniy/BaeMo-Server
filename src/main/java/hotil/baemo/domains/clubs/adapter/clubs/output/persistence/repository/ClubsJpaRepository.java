package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.ClubsEntity;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.dsl.ClubsQueryDsl;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubsJpaRepository extends JpaRepository<ClubsEntity, Long>, ClubsQueryDsl {

    @Query("SELECT c FROM ClubsEntity c WHERE c.isDelete = false AND c.id = :id")
    @Override
    Optional<ClubsEntity> findById(@Param("id") Long clubId);

    default ClubsEntity loadById(Long id) {
        return findById(id)
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND));
    }

    default ClubsEntity loadById(ClubsId clubsId) {
        return findById(clubsId.clubsId())
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND));
    }
}