package hotil.baemo.domains.exercise.application.ports.output;

import hotil.baemo.domains.exercise.domain.service.ClubService;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

import java.util.List;

public interface ExerciseExternalClubOutputPort extends ClubService {
    List<ClubId> getClubIds(UserId userId);

    List<UserId> getClubUserIds(ClubId clubId);
}
