package hotil.baemo.domains.clubs.adapter.post.output.persistence.repository.jpa;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.entity.ClubsPostEntity;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ClubsPostJpaRepository extends JpaRepository<ClubsPostEntity, Long> {
    @NonNull
    @Override
    @Query("SELECT p " +
        "FROM ClubsPostEntity p " +
        "WHERE p.isDelete = false " +
        "AND p.clubsPostId = :clubsPostId")
    Optional<ClubsPostEntity> findById(@NonNull @Param("clubsPostId") Long clubsPostId);

    default ClubsPostEntity loadById(final ClubsPostId clubsPostId) {
        return this.findById(clubsPostId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_POST_NOT_FOUND));
    }
}
