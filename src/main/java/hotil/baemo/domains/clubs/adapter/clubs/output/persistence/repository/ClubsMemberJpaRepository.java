package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClubsMemberJpaRepository extends JpaRepository<ClubsMemberEntity, Long> {
    @Query("""
        SELECT m
        FROM ClubsMemberEntity m
        WHERE m.isDelete = false
            AND m.usersId = :usersId
            AND m.clubsId = :clubsId
        """)
    Optional<ClubsMemberEntity> findByUsersIdAndClubsId(@Param("usersId") Long usersId, @Param("clubsId") Long clubsId);

    @Query("""
        SELECT m
        FROM ClubsMemberEntity m
        WHERE m.isDelete = false
            AND m.clubsId = :clubId
            AND m.clubRole = :clubsRole
        """)
    Optional<ClubsMemberEntity> findByClubIdAndClubRole(@Param("clubId") Long clubId, @Param("clubsRole") ClubsRole clubsRole);

    long countByClubsId(Long clubsId);

    boolean existsByUsersIdAndClubsId(Long usersId, Long clubsId);

    default ClubsMemberEntity loadByUsersIdAndClubsId(Long usersId, Long clubsId) {
        return findByUsersIdAndClubsId(usersId, clubsId)
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND_MEMBER));
    }

    default ClubsMemberEntity loadByUsersIdAndClubsId(ClubsUserId clubsUserId, ClubsId clubsId) {
        return findByUsersIdAndClubsId(clubsUserId.id(), clubsId.clubsId())
            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND_MEMBER));
    }

//    default ClubsMemberEntity loadByClubsIdAndClubRole(ClubsUserId clubsUserId, ClubsRole clubsRole) {
//        return findByUserIdAndClubRole(clubsUserId.id(), clubsRole)
//            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND_MEMBER));
//    }
//
//    default ClubsMemberEntity loadByClubsIdAndClubRole(Long usersId, ClubsRole clubsRole) {
//        return findByUserIdAndClubRole(usersId, clubsRole)
//            .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND_MEMBER));
//    }
}