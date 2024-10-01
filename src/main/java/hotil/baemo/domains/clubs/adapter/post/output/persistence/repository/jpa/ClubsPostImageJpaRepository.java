package hotil.baemo.domains.clubs.adapter.post.output.persistence.repository.jpa;

import hotil.baemo.domains.clubs.adapter.post.output.persistence.entity.ClubsPostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubsPostImageJpaRepository extends JpaRepository<ClubsPostImageEntity, Long> {

    @Query("SELECT i " +
        "FROM ClubsPostImageEntity i " +
        "WHERE i.isDeleted = false " +
        "AND i.clubsPostId = :clubsPostId")
    List<ClubsPostImageEntity> findAllByClubsPostId(@Param("clubsPostId") Long clubsPostId);

    @Modifying
    @Query("UPDATE ClubsPostImageEntity i " +
        "SET i.isDeleted = true " +
        "WHERE i.clubsPostId = :clubsPostId " +
        "AND i.isDeleted = false")
    void updateIsDelByClubsPostId(@Param("clubsPostId") Long clubsPostId);

    @Modifying
    @Query("DELETE FROM ClubsPostImageEntity e WHERE e.clubsPostId = :clubsPostId AND e.isDeleted = false")
    void deleteAllByClubPostId(@Param("clubsPostId") Long clubsPostId);
}