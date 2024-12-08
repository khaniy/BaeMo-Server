package hotil.baemo.support.domain.club;

import hotil.baemo.domains.clubs.adapter.output.persist.club.entity.ClubsEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.member.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.output.persist.club.repository.ClubsJpaRepository;
import hotil.baemo.domains.clubs.adapter.output.persist.member.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.application.ports.output.club.ClubEventOutputPort;
import hotil.baemo.domains.clubs.application.ports.output.member.ClubMemberEventOutputPort;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubSupport extends FixtureMonkeyBaseSupport {
    @Autowired
    private ClubsJpaRepository clubsJpaRepository;
    @Autowired
    private ClubsMemberJpaRepository clubsMemberJpaRepository;
    @Autowired
    private ClubEventOutputPort clubEventOutputPort;
    @Autowired
    private ClubMemberEventOutputPort clubMemberEventOutputPort;

    private Long clubsId;

    public Long setClubsAdmin(final Long clubsUserId) {
        final var clubsEntity = monkey.giveMeBuilder(ClubsEntity.class)
            .setNull("id")
            .set("creator", clubsUserId)
            .set("isDelete", false)
            .sample();
        final var savedClubsEntity = clubsJpaRepository.save(clubsEntity);

        this.clubsId = savedClubsEntity.getId();

        final var clubsMemberEntity = monkey.giveMeBuilder(ClubsMemberEntity.class)
            .set("clubsId", clubsId)
            .set("usersId", clubsUserId)
            .set("clubRole", ClubRole.ADMIN)
            .set("isDelete", false)
            .sample();

        clubsMemberJpaRepository.save(clubsMemberEntity);

        clubEventOutputPort.sendCreatedEvent(
            new UserId(clubsUserId),
            new ClubId(savedClubsEntity.getId())
        );
        return clubsId;
    }


    public void setPendingUser(Long clubId, Long newUserId) {
        final var clubsMemberEntity = monkey.giveMeBuilder(ClubsMemberEntity.class)
            .set("clubsId", clubId)
            .set("usersId", newUserId)
            .set("clubRole", ClubRole.PENDING)
            .set("isDelete", false)
            .sample();

        clubsMemberJpaRepository.save(clubsMemberEntity);
    }


    public void join(final Long clubsId, final Long userId) {
        final var clubsMemberEntity = monkey.giveMeBuilder(ClubsMemberEntity.class)
            .set("clubsId", clubsId)
            .set("usersId", userId)
            .set("clubRole", ClubRole.MEMBER)
            .set("isDelete", false)
            .sample();

        clubsMemberJpaRepository.save(clubsMemberEntity);
        clubMemberEventOutputPort.sendJoinUserEvent(
            new UserId(userId),
            new ClubId(clubsId)
        );
    }

    public void join(final Long clubsId, final Long userId, ClubRole role) {
        final var clubsMemberEntity = monkey.giveMeBuilder(ClubsMemberEntity.class)
            .set("clubsId", clubsId)
            .set("usersId", userId)
            .set("clubRole", role)
            .set("isDelete", false)
            .sample();

        clubsMemberJpaRepository.save(clubsMemberEntity);
        clubMemberEventOutputPort.sendJoinUserEvent(
            new UserId(userId),
            new ClubId(clubsId)
        );
    }


    public Long getClubsId() {
        return clubsId;
    }
}
