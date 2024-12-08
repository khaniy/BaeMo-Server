package hotil.baemo.domains.clubs.domain.policy.club;

import hotil.baemo.domains.clubs.domain.entity.club.Club;
import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.entity.member.ClubMember;
import hotil.baemo.domains.clubs.domain.value.club.*;
import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import hotil.baemo.domains.clubs.domain.value.member.UserId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateClubPolicy {

    public static ExecuteStep execute(UserId userId) {
        return ExecuteStep.of(userId);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class ExecuteStep {

        private final UserId userId;

        public PersistStep create(
            ClubName clubName,
            ClubSimpleDescription clubSimpleDescription,
            ClubDescription clubDescription,
            ClubLocation clubLocation,
            ClubImage clubImage
        ) {
            Club club = Club.builder()
                .clubName(clubName)
                .clubSimpleDescription(clubSimpleDescription)
                .clubDescription(clubDescription)
                .clubLocation(clubLocation)
                .build();
            return PersistStep.of(club, userId, clubImage);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class PersistStep {

        private final Club club;
        private final UserId userId;
        private final ClubImage clubImage;

        public EventStep persist(
            final BiFunction<Club, ClubImage, ClubId> saveClub,
            final Consumer<ClubMember> saveClubMember
        ) {
            ClubId clubId = saveClub.apply(club, clubImage);
            saveClubMember.accept(ClubMember.builder()
                .clubId(clubId)
                .role(ClubRole.ADMIN)
                .userId(userId)
                .build()
            );

            return EventStep.of(userId, clubId);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
    public static class EventStep {

        private final UserId userId;
        private final ClubId clubId;

        public ClubId produce(final BiConsumer<UserId, ClubId> producer) {
            producer.accept(userId, clubId);
            return clubId;
        }
    }

}
