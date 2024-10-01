package hotil.baemo.domains.notification.adapter.output.persist;

import hotil.baemo.domains.notification.adapter.output.persist.repository.ClubQRepository;
import hotil.baemo.domains.notification.application.port.output.QueryClubOutPort;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryClubPersistAdapter implements QueryClubOutPort {

    private final ClubQRepository clubQRepository;

    @Override
    public ClubTitle getClubTitle(ExerciseId exerciseId) {
        String clubTitle = clubQRepository.findClubTitleByExerciseId(exerciseId.id());
        return new ClubTitle(clubTitle);
    }

    @Override
    public ClubTitle getClubTitle(ClubId clubId) {
        String clubTitle = clubQRepository.findClubTitle(clubId.id());
        return new ClubTitle(clubTitle);
    }
}
