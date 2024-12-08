package hotil.baemo.domains.clubs.adapter.output.persist.post.repository;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.output.persist.post.entity.ClubsPostEntity;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
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

    default ClubsPostEntity loadById(final ClubPostId clubPostId) {
        return this.findById(clubPostId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_POST_NOT_FOUND));
    }
}
