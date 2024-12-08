package hotil.baemo.domains.exercise.domain.entity.court;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.validator.BaemoValidator;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ExerciseCourts extends BaemoValidator {

    @NotNull
    private List<ExerciseCourt> courts;

    private ExerciseCourts(List<ExerciseCourt> courts) {
        this.courts = courts;
        this.valid();

    }

    public static ExerciseCourts of(List<ExerciseCourt> matchUsers) {
        return new ExerciseCourts(matchUsers);
    }

    public void add(ExerciseCourt court) {
        checkDuplicateCourts(court);
        courts.add(court);

    }

    public boolean has(CourtNumber courtNumber) {
        return this.courts.stream().anyMatch(c -> c.getNumber().equals(courtNumber));
    }

    private void checkDuplicateCourts(ExerciseCourt court) {
        if (this.courts.stream().anyMatch(c -> c.getNumber().equals(court.getNumber()))) {
            throw new CustomException(ResponseCode.EXERCISE_COURT_DUPLICATED);
        }
    }
}