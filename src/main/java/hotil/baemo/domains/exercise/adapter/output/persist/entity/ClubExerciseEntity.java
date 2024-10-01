package hotil.baemo.domains.exercise.adapter.output.persist.entity;

import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@SuperBuilder
@Entity
@Table(name = "tb_exercise_club")
@NoArgsConstructor
@DiscriminatorValue("CLUB")
public class ClubExerciseEntity extends ExerciseEntity {

    @Column(name = "clubs_id")
    private Long clubId;
    private Integer guestLimit;
    private Integer currentParticipantGuest;

    public ClubExerciseEntity(Long id, ExerciseType exerciseType, String title, String description, ExerciseStatus exerciseStatus, Integer participantLimit, Integer currentParticipant, String location, ZonedDateTime exerciseStartTime, ZonedDateTime exerciseEndTime, String thumbnailUrl, boolean isDel, Long clubId, Integer guestLimit, Integer currentParticipantGuest) {
        super(id, exerciseType, title, description, exerciseStatus, participantLimit, currentParticipant, location, exerciseStartTime, exerciseEndTime, thumbnailUrl, isDel);
        this.clubId = clubId;
        this.guestLimit = guestLimit;
        this.currentParticipantGuest = currentParticipantGuest;
    }
}
