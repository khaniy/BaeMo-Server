package hotil.baemo.domains.clubs.adapter.output.persist.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.QClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.member.mapper.ClubMemberMapper;
import hotil.baemo.domains.clubs.adapter.output.persist.member.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.application.ports.output.member.CommandClubMemberOutputPort;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandClubMemberAdapter implements CommandClubMemberOutputPort {

    private final ClubsMemberJpaRepository clubsMemberJpaRepository;
    private static final QClubsMemberEntity MEMBER = QClubsMemberEntity.clubsMemberEntity;
    private final JPAQueryFactory factory;

    @Override
    public void saveUser(ClubMember clubMember) {
        clubsMemberJpaRepository.save(ClubMemberMapper.convert(clubMember));
    }

    @Override
    public ClubMember loadClubUser(UserId userId, ClubId clubId) {
        return clubsMemberJpaRepository.findByUserIdAndClubId(userId.id(), clubId.clubsId())
            .map(ClubMemberMapper::convert)
            .orElseGet(() -> ClubMember.builder()
                .userId(userId)
                .clubId(clubId)
                .role(ClubRole.NON_MEMBER)
                .build());
    }

    @Override
    public ClubMember loadDelegateUser(ClubId clubId) {
        final var user = factory
            .select(MEMBER)
            .from(MEMBER)
            .where(MEMBER.clubsId.eq(clubId.clubsId())
                .and(MEMBER.isDelete.isFalse())
                .and(MEMBER.clubRole.in(ClubRole.MANAGER, ClubRole.MEMBER)))
            .orderBy(MEMBER.clubRole
                    .when(ClubRole.MANAGER).then(0)
                    .otherwise(1).asc(),
                MEMBER.id.asc()
            )
            .fetchFirst();
        return user == null ? null : ClubMemberMapper.convert(user);
    }

    @Override
    public void delete(ClubMember clubMember) {
        ClubsMemberEntity entity = ClubMemberMapper.convert(clubMember);
        entity.delete();
        clubsMemberJpaRepository.save(entity);
    }

    @Override
    public void deleteAllByUsersId(UserId userId) {
        List<ClubsMemberEntity> allByUserId = clubsMemberJpaRepository.findAllByUserId(userId.id());
        clubsMemberJpaRepository.deleteAll(allByUserId);
    }
}
