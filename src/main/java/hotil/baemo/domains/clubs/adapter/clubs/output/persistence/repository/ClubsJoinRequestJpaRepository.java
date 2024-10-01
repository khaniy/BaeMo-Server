package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository;

import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.JoinRequestEntity;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.dsl.ClubsJoinQueryDsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ClubsJoinRequestJpaRepository extends JpaRepository<JoinRequestEntity, Long>, ClubsJoinQueryDsl {

    boolean existsByClubsIdAndNonMemberId(Long clubsId, Long nonMemberId);
    @Transactional
    void deleteAllByClubsIdAndNonMemberId(Long clubsId, Long nonMemberId);
}
