package hotil.baemo.domains.clubs.adapter.output.persist.club.repository;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.output.persist.club.entity.ClubsEntity;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubsJpaRepository extends JpaRepository<ClubsEntity, Long> {

    @Query("SELECT c FROM ClubsEntity c WHERE c.isDelete = false AND c.id = :id")
    @Override
    Optional<ClubsEntity> findById(@Param("id") Long clubId);

    default ClubsEntity loadById(Long id) {
        return findById(id)
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND));
    }

    default ClubsEntity loadById(ClubId clubId) {
        return findById(clubId.clubsId())
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND));
    }
}