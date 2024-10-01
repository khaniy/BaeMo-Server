package hotil.baemo.domains.exercise.domain.value.exercise;

import lombok.Getter;

@Getter
public enum ExerciseType {
    IMPROMPTU("번개 운동"),
    CLUB("클럽 운동");

    private final String status;

    private ExerciseType(String status) {
        this.status = status;
    }
}
