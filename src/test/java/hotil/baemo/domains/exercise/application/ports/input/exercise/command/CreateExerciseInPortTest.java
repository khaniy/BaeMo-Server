package hotil.baemo.domains.exercise.application.ports.input.exercise.command;

import hotil.baemo.domains.exercise.application.usecases.exercise.command.CreateExerciseUseCase;
import hotil.baemo.domains.exercise.domain.value.ClubExerciseVOGroup;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.domain.club.ClubSupport;
import hotil.baemo.support.domain.user.UserSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateExerciseInPortTest extends FixtureMonkeyBaseSupport {

    @Autowired
    private CreateExerciseUseCase createExerciseUseCase;
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ClubSupport clubSupport;

    UserId userId;
    ClubId clubId;

    @BeforeEach
    void setUp() {
        Long userId = userSupport.appendSampleUser();
        this.userId = new UserId(userId);
        Long clubId = clubSupport.setClubsAdmin(userId);
        this.clubId = new ClubId(clubId);
    }

    @Test
    void createClubExercise() {
        //given
        ClubExerciseVOGroup clubExerciseVOGroup = ClubExerciseVOGroup.builder()
            .title(monkey.giveMeOne(Title.class))
            .description(monkey.giveMeOne(Description.class))
            .participantLimit(monkey.giveMeOne(ParticipantNumber.class))
            .guestLimit(monkey.giveMeOne(ParticipantNumber.class))
            .location(monkey.giveMeOne(Location.class))
            .address(monkey.giveMeOne(Address.class))
            .locationCode(monkey.giveMeOne(LocationCode.class))
            .coordinate(monkey.giveMeOne(Coordinate.class))
            .exerciseTime(new ExerciseTime(ZonedDateTime.now(), ZonedDateTime.now().plusHours(2L)))
            .build();
        createExerciseUseCase.createClubExercise(userId, clubId, clubExerciseVOGroup);

        //when

        //then
    }
}