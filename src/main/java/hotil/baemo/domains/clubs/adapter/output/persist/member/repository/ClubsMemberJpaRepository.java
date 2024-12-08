package hotil.baemo.domains.clubs.adapter.output.persist.member.repository;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubsMemberJpaRepository extends JpaRepository<ClubsMemberEntity, Long> {
    @Query("""
        SELECT m
        FROM ClubsMemberEntity m
        WHERE m.isDelete = false
            AND m.usersId = :usersId
            AND m.clubsId = :clubId
        """)
    Optional<ClubsMemberEntity> findByUserIdAndClubId(@Param("usersId") Long usersId, @Param("clubId") Long clubId);

    @Query("""
        SELECT m
        FROM ClubsMemberEntity m
        WHERE m.isDelete = false
            AND m.clubsId = :clubId
            AND m.clubRole = :clubRole
        """)
    Optional<ClubsMemberEntity> findByClubIdAndClubRole(@Param("clubId") Long clubId, @Param("clubRole") ClubRole clubRole);

    @Query("""
        SELECT m
        FROM ClubsMemberEntity m
        WHERE m.isDelete = false
            AND m.usersId = :userId
        """)
    List<ClubsMemberEntity> findAllByUserId(@Param("userId") Long id);

    long countByClubsId(Long clubId);

    default ClubsMemberEntity loadByUsersIdAndClubsId(Long usersId, Long clubId) {
        return findByUserIdAndClubId(usersId, clubId)
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND_MEMBER));
    }

    default ClubsMemberEntity loadByUsersIdAndClubsId(UserId userId, ClubId clubId) {
        return findByUserIdAndClubId(userId.id(), clubId.clubsId())
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND_MEMBER));
    }
}